package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostDto {
    private String title;
    private String activityDetail;
    private PassionSize passionSize;
    private String additionalWriting;
    private String openChatUrl;
}
