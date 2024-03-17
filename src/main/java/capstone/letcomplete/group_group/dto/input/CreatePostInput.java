package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "모집글을 작성할 게시판ID")
    private Long boardId;

    @NotNull
    @Min(value = 0)
    @Schema(description = "작성자 ID")
    private Long writerId;

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
