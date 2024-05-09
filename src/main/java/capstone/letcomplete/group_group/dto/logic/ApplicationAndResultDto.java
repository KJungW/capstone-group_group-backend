package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationAndResultDto {
    private Long applicationId;
    private ApplicationState applicationState;
    private Long postId;
    private String postTitle;
    private String openChatUrl;
    private LocalDateTime createdTime;

    public void resetOpenChatUrl() {
        openChatUrl = "";
    }
}
