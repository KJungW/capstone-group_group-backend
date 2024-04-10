package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SaveApplicationInput;
import capstone.letcomplete.group_group.dto.logic.FileUploadResultDto;
import capstone.letcomplete.group_group.dto.logic.OrdinalFileUploadResultDto;
import capstone.letcomplete.group_group.dto.logic.SaveRequirementResultInput;
import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.entity.RequirementsFormResult;
import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import capstone.letcomplete.group_group.entity.valuetype.FileResult;
import capstone.letcomplete.group_group.entity.valuetype.Requirement;
import capstone.letcomplete.group_group.entity.valuetype.RequirementResult;
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
        
        // RequirementsFormResult.requirementResultList 데이터 준비
        // - 최종 결과물을 담을 변수 준비
        List<RequirementResult> requirementResultList = new ArrayList<>();

        // - 타입별로 SaveRequirementResultInput을 분리
        List<SaveRequirementResultInput> textTypeList =
                input.getRequirementResultList()
                        .stream()
                        .filter(requirementResult -> requirementResult.getType() == RequirementResultType.TEXT)
                        .toList();
        List<SaveRequirementResultInput> fileTypeList =
                input.getRequirementResultList()
                        .stream()
                        .filter(requirementResult -> requirementResult.getType() == RequirementResultType.FILE)
                        .toList();

        // - text 타입일 경우 처리
        for (SaveRequirementResultInput textTypeInput : textTypeList) {
            requirementResultList.add(new TextResult(textTypeInput.getContent()));
        }

        // - file 타입일 경우 처리
        List<MultipartFile> ordinalInputFiles =
                fileTypeList.stream()
                .map(
                        requirementResultInput -> inputFiles.stream()
                                .filter(file -> file.getOriginalFilename().equals(requirementResultInput.getContent()))
                                .findFirst().get()
                )
                .toList();
        String fileUploadDir = input.getApplicantId().toString() + targetForm.getId();
        List<OrdinalFileUploadResultDto> fileUploadResultDtos = s3Service.uploadFiles(ordinalInputFiles, fileUploadDir);
        for (int i = 0; i < fileUploadResultDtos.size(); i++) {
            FileUploadResultDto fileUploadResultDto = fileUploadResultDtos.get(i).getFileUploadResult();
            requirementResultList.add(
                    new FileResult(
                            fileUploadResultDto.getServerFileName(),
                            fileUploadResultDto.getOriginFileName(),
                            fileUploadResultDto.getUploadUrl()
                    )
            );
        }

        // - requirementResults를 json으로 인코딩
        String jsonRequirementResults = jsonUtil.convertObjectToJson(requirementResultList);

        // RequirementsFormResult DB저장
        RequirementsFormResult newFormResult = RequirementsFormResult.makeRequirementsFormResult(targetForm, jsonRequirementResults);
        formResultRepository.save(newFormResult);
        return newFormResult.getId();
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

}
