package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreatePostInput {
    @NotNull
    @Min(value = 0)
    private Long boardId;

    @NotNull
    @Min(value = 0)
    private Long writerId;

    @NotBlank
    private String title;

    @NotBlank
    private String activityDetail;

    @NotNull
    private PassionSize passionSize;

    private String additionalWriting;

    @NotBlank
    private String openChatUrl;

    private List<CreateRequirementInput> requirementList;

    public CreatePostInput(Long boardId, Long writerId, String title, String activityDetail,
                           PassionSize passionSize, String additionalWriting, String openChatUrl,
                           List<CreateRequirementInput> requirementList) {
        this.boardId = boardId;
        this.writerId = writerId;
        this.title = title;
        this.activityDetail = activityDetail;
        this.passionSize = passionSize;
        this.additionalWriting = additionalWriting;
        this.openChatUrl = openChatUrl;
        this.requirementList = requirementList;
        initCreatePostInput();
    }

    private void initCreatePostInput() {
        if(additionalWriting.isEmpty())
            additionalWriting="";
        if(requirementList==null){
            requirementList = new ArrayList<>();
        }
    }
}
