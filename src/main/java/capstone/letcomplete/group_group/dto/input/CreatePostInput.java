package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
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
}
