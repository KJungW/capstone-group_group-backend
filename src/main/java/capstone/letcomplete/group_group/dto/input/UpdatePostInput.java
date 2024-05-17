package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UpdatePostInput {
    @NotNull
    @Schema(description = "모집글 ID")
    private Long postId;

    @NotBlank
    @Schema(description = "모집글 제목")
    private String title;

    @NotBlank
    @Schema(description = "활용 내용")
    private String activityDetail;

    @NotNull
    @Schema(description = "열정 정도")
    private PassionSize passionSize;

    @Schema(description = "더하고 싶은말")
    private String additionalWriting;

    @NotBlank
    @Schema(description = "오픈채팅방 URL")
    private String openChatUrl;

    @Schema(description = "참여요건 리스트")
    private List<CreateRequirementInput> requirementList;
}
