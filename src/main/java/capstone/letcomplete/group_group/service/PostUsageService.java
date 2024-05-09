package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.entitymake.MakePostDto;
import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.dto.input.CreateRequirementInput;
import capstone.letcomplete.group_group.dto.logic.*;
import capstone.letcomplete.group_group.dto.output.GetPostDetailByMemberOutput;
import capstone.letcomplete.group_group.dto.output.GetPostDetailOutput;
import capstone.letcomplete.group_group.dto.output.GetRequirementOutput;
import capstone.letcomplete.group_group.entity.Board;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.Post;
import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.entity.valuetype.Requirement;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsageService {
    private final PostService postService;
    private final RequirementsFormService formService;;
    private final BoardService boardService;
    private final MemberService memberService;
    private final ApplicationService applicationService;
    private final JsonUtil jsonUtil;

    @Transactional
    public Long savePost(CreatePostInput input) throws JsonProcessingException {
        Post newPost = makePostByInput(input);
        RequirementsForm newForm = makeFormByInput(newPost, input.getRequirementList());

        postService.savePost(newPost);
        formService.SaveRequirementsForm(newForm);
        return newPost.getId();
    }

    public GetPostDetailOutput getPostDetail(Long id) throws JsonProcessingException {
        Post post = postService.findById(id);
        RequirementsForm form = formService.findNewestWithPostId(id);
        return new GetPostDetailOutput(
                post.getBoard().getId(), post.getBoard().getTitle(), post.getWriter().getId(), post.getWriter().getNickName(),
                post.getTitle(), post.getActivityDetail(), post.getPassionSize(), post.getAdditionalWriting(),
                makeRequirementOutputsByForm(form)
        );
    }

    public GetPostDetailByMemberOutput getPostDetailByMember(Long postId, Long memberId) throws JsonProcessingException {
        Post post = postService.findById(postId);
        RequirementsForm form = formService.findNewestWithPostId(postId);
        GetPostDetailByMemberOutput result = new GetPostDetailByMemberOutput(
                post.getBoard().getId(), post.getBoard().getTitle(), post.getWriter().getId(), post.getWriter().getNickName(),
                post.getTitle(), post.getActivityDetail(), post.getPassionSize(), post.getAdditionalWriting(),
                post.getOpenChatUrl(), makeRequirementOutputsByForm(form)
        );
        if(!post.getWriter().getId().equals(memberId)) {
            result.removeOpenChatUrl();
        }
        return result;
    }

    private Post makePostByInput(CreatePostInput input) {
        Board inputBoard = boardService.findById(input.getBoardId());
        Member inputWriter = memberService.findById(input.getWriterId());
        return Post.makePost(
                new MakePostDto(
                        inputBoard, inputWriter, input.getTitle(),
                        input.getActivityDetail(), input.getPassionSize(),
                        input.getAdditionalWriting(), input.getOpenChatUrl()
                )
        );
    }

    private RequirementsForm makeFormByInput(Post post, List<CreateRequirementInput> requirementInputs) throws JsonProcessingException {
        List<Requirement> requirements = makeRequirementsByInput(requirementInputs);
        String requirementsJson = makeRequirementsToJson(requirements);
        return RequirementsForm.makeRequirementsForm(post, requirementsJson);
    }

    private List<Requirement> makeRequirementsByInput(List<CreateRequirementInput> requirementInputList) {
        return requirementInputList.stream()
                .map((input) -> new Requirement(UUID.randomUUID().toString(), input.getTitle(), input.getResultType()))
                .toList();
    }

    private String makeRequirementsToJson(List<Requirement> requirementList) throws JsonProcessingException {
        return jsonUtil.convertObjectToJson(requirementList);
    }

    private List<GetRequirementOutput> makeRequirementOutputsByForm(RequirementsForm form) throws JsonProcessingException {
        List<Requirement> requirements = jsonUtil.convertJsonToList(form.getRequirements(), Requirement.class);
        return requirements.stream()
                .map(requirement -> new GetRequirementOutput(requirement.getId(), requirement.getTitle(), requirement.getResultType()))
                .toList();
    }

    public PostAndApplicationsDto findPostAndApplicationsByMember(int pageNumber, int pageSize, Long memberId) {
        // 현재 멤버가 작성한 모든 모집글 리스트 조회
        PostOverViewsInMemberDto postOverViewInMember = postService.findPostOverViewInMember(pageNumber, pageSize, memberId);

        // 모집글마다의 신청 리스트 조회
        List<Long> postIdList = postOverViewInMember.getContents().stream()
                .map(PostOverViewDto::getId)
                .collect(Collectors.toList());
        List<ApplicationOverviewsInPostDto> applicationOverViewsByPosts = applicationService.findApplicationOverViewsByPosts(postIdList);

        // 데이터 재구성
        List<PostAndApplicationsOverviewDto> postAndApplicationsOverviewList= new ArrayList<>();
        for (PostOverViewDto postOverView : postOverViewInMember.getContents()) {
            PostAndApplicationsOverviewDto postAndApplicationsOverview = new PostAndApplicationsOverviewDto(
                    postOverView.getId(),
                    postOverView.getTitle(),
                    postOverView.getCreateDate(),
                    new ArrayList<>()
            );
            for(ApplicationOverviewsInPostDto applicationOverViews :applicationOverViewsByPosts) {
                if(postOverView.getId().equals(applicationOverViews.getPostId())) {
                    postAndApplicationsOverview.changeApplicationOverViewList(applicationOverViews.getApplicationOverviewDtoList());
                    break;
                }
            }
            postAndApplicationsOverviewList.add(postAndApplicationsOverview);
        }

        return new PostAndApplicationsDto(postAndApplicationsOverviewList, postOverViewInMember.getTotalPages(), postOverViewInMember.getCurrentPageNumber(), postOverViewInMember.isLastPage(), postOverViewInMember.isFirstPage());
    }

    @Transactional
    public void deletePost(Long postId, Long memberId) throws JsonProcessingException {
        Post post = postService.findById(postId);
        checkPostWriter(post, memberId);

        applicationService.deleteAllByPost(postId);
        formService.deleteAllByPost(postId);
        postService.deleteById(postId);
    }

    private void checkPostWriter(Post post, Long memberId) {
        if(!post.getWriter().getId().equals(memberId)) {
            throw new InvalidInputException("잘못된 입력입니다.");
        }
    }


}
