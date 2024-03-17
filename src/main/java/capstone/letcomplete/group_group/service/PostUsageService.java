package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.entitymake.MakePostDto;
import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.dto.input.CreateRequirementInput;
import capstone.letcomplete.group_group.dto.output.GetPostDetailOutput;
import capstone.letcomplete.group_group.dto.output.GetRequirementOutput;
import capstone.letcomplete.group_group.entity.Board;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.Post;
import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.entity.valuetype.Requirement;
import capstone.letcomplete.group_group.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostUsageService {
    private final PostService postService;
    private final RequirementsFormService formService;;
    private final BoardService boardService;
    private final MemberService memberService;
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
                post.getTitle(), post.getActivityDetail(), post.getPassionSize(), post.getAdditionalWriting(), post.getOpenChatUrl(),
                makeRequirementOutputsByForm(form)
        );
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
                .map((input) -> new Requirement(input.getTitle(), input.getResultType()))
                .toList();
    }

    private String makeRequirementsToJson(List<Requirement> requirementList) throws JsonProcessingException {
        return jsonUtil.convertObjectToJson(requirementList);
    }

    private List<GetRequirementOutput> makeRequirementOutputsByForm(RequirementsForm form) throws JsonProcessingException {
        List<Requirement> requirements = jsonUtil.convertJsonToList(form.getRequirements(), Requirement.class);
        return requirements.stream()
                .map(requirement -> new GetRequirementOutput(requirement.getTitle(), requirement.getResultType()))
                .toList();
    }

}
