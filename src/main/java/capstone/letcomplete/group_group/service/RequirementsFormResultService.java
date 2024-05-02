package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SaveApplicationInput;
import capstone.letcomplete.group_group.dto.input.SaveRequirementResultInput;
import capstone.letcomplete.group_group.dto.logic.*;
import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.entity.RequirementsFormResult;
import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import capstone.letcomplete.group_group.entity.valuetype.FileResult;
import capstone.letcomplete.group_group.entity.valuetype.Requirement;
import capstone.letcomplete.group_group.entity.valuetype.TextResult;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.repository.RequirementsFormResultRepository;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequirementsFormResultService {
    private final RequirementsFormResultRepository formResultRepository;
    private final RequirementsFormService requirementsFormService;
    private final S3Service s3Service;
    private final JsonUtil jsonUtil;

    @Transactional
    public Long save(SaveApplicationInput input, List<MultipartFile> inputFiles) throws IOException {
        // 조회
        // - RequirementsFormResult 구성요소 targetForm 세팅
        RequirementsForm targetForm = requirementsFormService.findNewestWithPostId(input.getPostId());

        // 검증
        // - 입력값 중 참여요건 ID값(input)이 유효한지 체크
        if(!checkRequirementIdInInput(targetForm, input)) {
            throw new InvalidInputException("참여요건 ID값이 유효하지 않습니다.");
        }
        // - 참여요건 제출물 중 파일이 제대로 입력되었는지 체크
        if(!checkFileInInput(input.getRequirementResultList(), inputFiles)) {
            throw new InvalidInputException("파일이 제대로 전송되지않았습니다.");
        }

        // 텍스트 타입 처리
        // - 텍스트 타입의 SaveRequirementResultInput들을 분리
        List<SaveRequirementResultInput> textTypeList =
                input.getRequirementResultList()
                        .stream()
                        .filter(requirementResult -> requirementResult.getType() == RequirementResultType.TEXT)
                        .toList();
        // - text 타입일 경우 처리
        List<TextResult> textResults = convertSaveRequirementResultInputsToTextResults(textTypeList);

        // 파일 타입 처리
        // - 파일 타입의 SaveRequirementResultInput들을 분리
        List<SaveRequirementResultInput> fileTypeList =
                input.getRequirementResultList()
                        .stream()
                        .filter(requirementResult -> requirementResult.getType() == RequirementResultType.FILE)
                        .toList();
        // - file 타입일 경우 처리
        String fileUploadDir = input.getApplicantId().toString() + "-" + targetForm.getId();
        List<FileResult> fileResults = convertSaveRequirementResultInputsToFileResult(fileTypeList, inputFiles, fileUploadDir);

        // - DB에 저장할 전체 제출물 데이터 객체 생성
        AllRequirementResultsInJson allResult = new AllRequirementResultsInJson(textResults, fileResults);
        // - 전체 제출물 데이터를 JSON으로 인코딩
        String jsonRequirementResults = jsonUtil.convertObjectToJson(allResult);
        // - DE저장
        RequirementsFormResult newFormResult = RequirementsFormResult.makeRequirementsFormResult(targetForm, jsonRequirementResults);
        formResultRepository.save(newFormResult);
        return newFormResult.getId();
    }

    private List<TextResult> convertSaveRequirementResultInputsToTextResults(List<SaveRequirementResultInput> textTypeList) {
        List<TextResult> textResults = new ArrayList<>();
        for (SaveRequirementResultInput textTypeInput : textTypeList) {
            textResults.add(new TextResult(textTypeInput.getRequirementId(), textTypeInput.getContent()));
        }
        return textResults;
    }

    private List<FileResult> convertSaveRequirementResultInputsToFileResult(List<SaveRequirementResultInput> fileTypeList, List<MultipartFile> inputFiles, String fileUploadDir) throws IOException {
        // 입력된 파일리스트와 파일을 요구하는 참여요건의 ID를 매칭
        List<RequirementFileResultData> requirementFileResultInputs = fileTypeList.stream().map(requirementResultInput -> {
            MultipartFile matchingFile = inputFiles.stream().filter(file -> file.getOriginalFilename().equals(requirementResultInput.getContent())).findFirst().get();
            return new RequirementFileResultData(requirementResultInput.getRequirementId(), matchingFile);
        }).toList();
        // - 파일 업로드
        List<MultipartFile> ordinalInputFiles = requirementFileResultInputs.stream().map(
                requirementFileResultInput -> requirementFileResultInput.getFileResult()).collect(Collectors.toList());
        List<OrdinalFileUploadResultDto> fileUploadResultDtos = s3Service.uploadFiles(ordinalInputFiles, fileUploadDir);
        // - DB에 저장할 file타입 제출물들에 대한 데이터리스트를 생성
        List<FileResult> fileResults = new ArrayList<>();
        for (int i = 0; i < requirementFileResultInputs.size(); i++) {
            String requirementId = requirementFileResultInputs.get(i).getRequirementId();
            FileUploadResultDto fileUploadResultDto = fileUploadResultDtos.get(i).getFileUploadResult();
            fileResults.add(
                    new FileResult(
                            requirementId,
                            fileUploadResultDto.getServerFileName(),
                            fileUploadResultDto.getOriginFileName(),
                            fileUploadResultDto.getUploadUrl()
                    )
            );
        }
        return fileResults;
    }

    public AllRequirementResultsInJson convertJsonToRequirementResults (String jsonData) throws JsonProcessingException {
        return jsonUtil.convertJsonToObject(jsonData, AllRequirementResultsInJson.class);
    }

    public RequirementsFormResult findById(Long id) {
        return formResultRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 RequirementsFormResult가 존재하지 않습니다.")
        );
    }

    private boolean checkRequirementIdInInput(RequirementsForm targetForm, SaveApplicationInput input) throws JsonProcessingException {
        List<Requirement> requirements = jsonUtil.convertJsonToList(targetForm.getRequirements(), Requirement.class);
        List<String> requirementsIds = requirements.stream()
                .map(Requirement::getId).toList();
        List<String> inputRequirementsIds = input.getRequirementResultList().stream()
                .map(SaveRequirementResultInput::getRequirementId).toList();
        return (requirementsIds.size() == inputRequirementsIds.size() &&  new HashSet<>(inputRequirementsIds).containsAll(requirementsIds));

    }

    private boolean checkFileInInput(List<SaveRequirementResultInput> input, List<MultipartFile> inputFiles) {
        if(inputFiles == null) return true;
        List<String> inputFileTitle = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            SaveRequirementResultInput requirementResultInput = input.get(i);
            if (requirementResultInput.getType() == RequirementResultType.FILE) {
                inputFileTitle.add(requirementResultInput.getContent());
            }
        }
        List<String> fileTile = inputFiles.stream().map(MultipartFile::getOriginalFilename).toList();
        return new HashSet<>(fileTile).containsAll(inputFileTitle);
    }

    @Transactional
    public void deleteById(Long id) throws JsonProcessingException {
        // 제거할 RequirementsFormResult 조회
        RequirementsFormResult formResult = findById(id);
        
        // 조회된 RequirementsFormResult의 파일 제출물 리스트 조회
        String requirementResultsJson = formResult.getRequirementResults();
        AllRequirementResultsInJson allRequirementResultsInJson = convertJsonToRequirementResults(requirementResultsJson);
        List<FileResult> fileResults = allRequirementResultsInJson.getFileResults();
        
        // 클라우드 스토리지에서 파일 제출물을 모두 제거
        List<String> fileNameList = fileResults.stream().map(FileResult::getExternalName).collect(Collectors.toList());
        s3Service.deleteAll(fileNameList);
        
        // RequirementsFormResult 제거
        formResultRepository.delete(formResult);
    }
}
