package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SaveApplicationInput;
import capstone.letcomplete.group_group.dto.logic.AllRequirementResultsInJson;
import capstone.letcomplete.group_group.dto.logic.ApplicationDetailDto;
import capstone.letcomplete.group_group.dto.logic.RequirementData;
import capstone.letcomplete.group_group.entity.*;
import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import capstone.letcomplete.group_group.entity.valuetype.FileResult;
import capstone.letcomplete.group_group.entity.valuetype.Requirement;
import capstone.letcomplete.group_group.entity.valuetype.TextResult;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.ApplicationRepository;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final RequirementsFormResultService requirementsFormResultService;
    private final JsonUtil jsonUtil;

    @Transactional
    public Long saveApplication(SaveApplicationInput input, List<MultipartFile> inputFiles) throws IOException {
        // Application의 구성요소 세팅
        Post post = postService.findById(input.getPostId());
        Member member = memberService.findById(input.getApplicantId());
        Long formResultId = requirementsFormResultService.save(input, inputFiles);
        RequirementsFormResult formResult = requirementsFormResultService.findById(formResultId);

        // Application 저장
        Application newApplication = Application.makeApplication(post, member, false, formResult);
        applicationRepository.save(newApplication);

        return newApplication.getId();
    }

    public ApplicationDetailDto findApplicationDetail(Long memberId, Long applicationId) throws JsonProcessingException {
        // 필요 데이터 조회 및 검증
        Application application = findById(applicationId);
        if(!application.getApplicant().getId().equals(memberId))
            throw new DataNotFoundException("id에 해당하는 Application이 존재하지 않습니다.");
        
        // JSON형태의 참여요건 데이터를 다시 객체로 디코딩
        RequirementsForm targetForm = application.getRequirementsFormResult().getTargetForm();
        List<Requirement> requirements = jsonUtil.convertJsonToList(targetForm.getRequirements(), Requirement.class);

        // JSON형태의 참여요건 제출물 데이터를 다시 객체로 디코딩
        String requirementResultsJson = application.getRequirementsFormResult().getRequirementResults();
        AllRequirementResultsInJson allRequirementResults = requirementsFormResultService.convertJsonToRequirementResults(requirementResultsJson);
        
        // 참여요건 데이터와 제출물 데이터를 형식에 맞게 재구성
        List<FileResult> fileResults = allRequirementResults.getFileResults();
        List<TextResult> textResults = allRequirementResults.getTextResults();

        List<RequirementData> requirementDataList = new ArrayList<>();
        for(Requirement requirement : requirements) {
            if(requirement.getResultType() == RequirementResultType.FILE) {
                FileResult matchingFileResult = fileResults.stream()
                        .filter(fileResult -> requirement.getId().equals(fileResult.getRequirementId()))
                        .findFirst().orElseThrow(()->new IllegalStateException("참여요건ID에 해당하는 제출물이 없음"));
                requirementDataList.add(new RequirementData(
                        matchingFileResult.getRequirementId(), requirement.getTitle(),
                        matchingFileResult.getType(), matchingFileResult.getUrl()));
            } else {
                TextResult matchingTextResult = textResults.stream()
                        .filter(textResult -> requirement.getId().equals(textResult.getRequirementId()))
                        .findFirst().orElseThrow(()->new IllegalStateException("참여요건ID에 해당하는 제출물이 없음"));
                requirementDataList.add(new RequirementData(
                        matchingTextResult.getRequirementId(), requirement.getTitle(),
                        matchingTextResult.getType(), matchingTextResult.getContent()));
            }
        }

        return new ApplicationDetailDto(
                application.getId(), application.getPost().getId(),
                application.getApplicant().getId(), application.isPassed(),
                requirementDataList
        );
    }

    public Application findById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 Application이 존재하지 않습니다.")
        );
    }


}
