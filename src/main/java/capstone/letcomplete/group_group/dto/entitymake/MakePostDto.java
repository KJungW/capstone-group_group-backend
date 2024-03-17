package capstone.letcomplete.group_group.dto.entitymake;

import capstone.letcomplete.group_group.entity.Board;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MakePostDto {
    private Board board;
    private Member writer;
    private String title;
    private String activityDetail;
    private PassionSize passionSize;
    private String additionalWriting;
    private String openChatUrl;
}
