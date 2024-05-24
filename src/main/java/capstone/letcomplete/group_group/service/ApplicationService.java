package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SaveApplicationInput;
import capstone.letcomplete.group_group.dto.logic.*;
import capstone.letcomplete.group_group.entity.*;
import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import capstone.letcomplete.group_group.entity.valuetype.FileResult;
import capstone.letcomplete.group_group.entity.valuetype.Requirement;
import capstone.letcomplete.group_group.entity.valuetype.TextResult;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.repository.ApplicationRepository;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final RequirementsFormResultService requirementsFormResultService;
    private final DisabledApplicationService disabledApplicationService;
    private final JsonUtil jsonUtil;

    @Transactional
    public Long saveApplication(SaveApplicationInput input, List<MultipartFile> inputFiles) throws IOException {
        // 검증
        checkApplicationForMyPost(input.getApplicantId(), input.getPostId());
        checkDuplicateApplication(input.getApplicantId(), input.getPostId());

        // Application의 구성요소 세팅과 검증
        Post post = postService.findById(input.getPostId());
        Member member = memberService.findById(input.getApplicantId());
        Long formResultId = requirementsFormResultService.save(input, inputFiles);
        RequirementsFormResult formResult = requirementsFormResultService.findById(formResultId);

        // Application 저장
        Application newApplication = Application.makeApplication(post, member, ApplicationState.YET, formResult);
        applicationRepository.save(newApplication);

        return newApplication.getId();
    }

    public ApplicationDetailDto findApplicationDetail(Long memberId, Long applicationId) throws JsonProcessingException {
        // 필요 데이터 조회 및 검증
        Application application = findFullApplicationById(applicationId);
        if(!application.getApplicant().getId().equals(memberId) && !application.getPost().getWriter().getId().equals(memberId))
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
                        matchingFileResult.getType(), matchingFileResult.getUrl(), matchingFileResult.getInternalName()));
            } else {
                TextResult matchingTextResult = textResults.stream()
                        .filter(textResult -> requirement.getId().equals(textResult.getRequirementId()))
                        .findFirst().orElseThrow(()->new IllegalStateException("참여요건ID에 해당하는 제출물이 없음"));
                requirementDataList.add(new RequirementData(
                        matchingTextResult.getRequirementId(), requirement.getTitle(),
                        matchingTextResult.getType(), matchingTextResult.getContent(), null));
            }
        }

        return new ApplicationDetailDto(
                application.getId(), application.getPost().getId(),
                application.getApplicant().getId(), application.getIsPassed(),
                requirementDataList
        );
    }

    public Application findById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 Application이 존재하지 않습니다.")
        );
    }

    public Application findFullApplicationById(Long id) {
        return applicationRepository.findFullApplicationById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 Application이 존재하지 않습니다.")
        );
    }

    public List<ApplicationOverviewsInPostDto> findApplicationOverViewsByPosts(List<Long> postIdList) {
        // postIdList에 속하는 모든 신청데이터 리스트 조회
        List<ApplicationOverviewDto> applicationsInPosts = applicationRepository.findApplicationsInPosts(postIdList);
        
        // postId에 따라 신청데이터 리스트 그루핑
        Map<Long, List<ApplicationOverviewDto>> groupingById = applicationsInPosts.stream()
                .collect(Collectors.groupingBy(ApplicationOverviewDto::getPostId));

        // 데이터 재구성
        List<ApplicationOverviewsInPostDto> result = new ArrayList<>();
        for(Long key :groupingById.keySet()) {
            List<ApplicationOverviewDto> applicationOverviewList = groupingById.get(key);
            result.add(new ApplicationOverviewsInPostDto(key, applicationOverviewList));
        }
        return result;
    }

    @Transactional
    public Long updateApplicationState(Long memberId, Long applicationId, ApplicationState applicationState) {
        // 상태를 변경시킬 신청데이터 조회
        Application application = findById(applicationId);

        // 신청데이터가 타겟으로 하는 모집글이 현재 사용자의 모집글이 맞는지 체크
        if(!application.getPost().getWriter().getId().equals(memberId))
            throw new InvalidInputException("현재 사용자가 작성한 모집글에 대한 신청이 아닙니다.");

        // 이미 수락/거부로 상태변경이 완료된 신청데이터인지 체크
        if(application.getIsPassed() != ApplicationState.YET)
            throw new InvalidInputException("이미 수락/거부로 상태가 변경된 신청입니다.");

        // 상태변경
        application.changeApplicationState(applicationState);
        return application.getId();
    }

    public ApplicationsByMemberDto findApplicationsByMember(int pageNumber, int pageSize, Long memberId) {
        // ApplicationAndResultDto 리스트 조회
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<ApplicationAndResultDto> findResult = applicationRepository.findApplicationsInMember(memberId, pageRequest);
        List<ApplicationAndResultDto> applicationsAndResultList = findResult.getContent();

        // 신청데이터가 수락된 상태가 아니라면 opentChatURL가 공개되지 않도록 세팅
        for(ApplicationAndResultDto applicationsAndResult : applicationsAndResultList) {
            if(applicationsAndResult.getApplicationState() != ApplicationState.ACCEPT) {
                applicationsAndResult.resetOpenChatUrl();
            }
        }

        return new ApplicationsByMemberDto(applicationsAndResultList, findResult.getTotalPages(),
                findResult.getNumber(), findResult.isLast(), findResult.isFirst()
        );
    }

    @Transactional
    public void deleteAllByPost(Long postId) throws JsonProcessingException {
        List<Application> applicationList = applicationRepository.findAllByPost(postId);
        deleteAll(applicationList.stream().map(Application::getId).collect(Collectors.toList()));
    }

    @Transactional
    public void deleteAll(List<Long> applicationIdList) throws JsonProcessingException {
        List<Application> applicationList = applicationRepository.findAllById(applicationIdList);
        List<Long> formResultIdList = applicationList.stream().map(app -> app.getRequirementsFormResult().getId()).toList();

        // disabledApplication 생성
        for(Application application : applicationList) {
            DisabledApplication disabledApplication = DisabledApplication.createDisabledApplication(
                    application.getId(), application.getApplicant().getId(),
                    application.getPost().getId(), application.getPost().getTitle(),
                    application.getIsPassed(), application.getCreateDate()
            );
            disabledApplicationService.save(disabledApplication);
        }

        // application 제거
        for(Application application : applicationList) {
            application.disconnectRequirementsFormResult();
        }
        applicationRepository.flush();
        applicationRepository.deleteAll(applicationList);

        // application과 연결된 RequirementsFormResult 제거
        for(Long id : formResultIdList) {
            requirementsFormResultService.deleteById(id);
        }
    }

    public List<Application> findAllApplicationsByMember(Long memberId) {
        return applicationRepository.findAllByMember(memberId);
    }

    public void checkApplicationForMyPost (Long applicantId, Long postId) {
        Post post = postService.findById(postId);
        if(post.getWriter().getId().equals(applicantId)){
            throw new InvalidInputException("자신이 작성한 모집글에 신청을 하는 것은 불가능합니다.");
        }
    }

    public void checkDuplicateApplication (Long memberId, Long postId) {
        List<Application> allApplicationsByMember = findAllApplicationsByMember(memberId);
        List<Application> checkedApplication = allApplicationsByMember.stream()
                .filter(app -> app.getPost().getId().equals(postId))
                .collect(Collectors.toList());
        if(!checkedApplication.isEmpty()) {
            throw new InvalidInputException("같은 모집글에 중복신청을 하는 것은 불가능합니다.");
        }
    }
}
