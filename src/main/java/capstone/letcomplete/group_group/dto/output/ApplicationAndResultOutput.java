package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.ApplicationAndResultDto;
import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationAndResultOutput {
    @Schema(description = "신청 ID")
    private Long applicationId;
    @Schema(description = "모집글 ID")
    private Long postId;
    @Schema(description = "모집글 제목")
    private String postTitle;
    @Schema(description = "신청 상태")
    private ApplicationState applicationState;
    @Schema(description = "오픈채팅방 주소")
    private String openChatUrl;

    public ApplicationAndResultOutput(ApplicationAndResultDto dto) {
        this.applicationId = dto.getApplicationId();
        this.postId = dto.getPostId();
        this.postTitle = dto.getPostTitle();
        this.applicationState = dto.getApplicationState();
        this.openChatUrl = dto.getOpenChatUrl();
    }
}
