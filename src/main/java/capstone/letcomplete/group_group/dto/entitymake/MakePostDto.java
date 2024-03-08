package capstone.letcomplete.group_group.dto.entitymake;

import capstone.letcomplete.group_group.entity.Board;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MakePostDto {
    private Board board;
    private Member writer;
    private String title;
    private String activityDetail;
    private PassionSize passionSize;
    private String additionalWriting;
    private String openChatUrl;
}
