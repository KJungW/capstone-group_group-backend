package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostDetailOutput {

    private Long boardId;
    private String boardName;

    private Long writerId;
    private String writerNickname;

    private String title;
    private String activityDetail;
    private PassionSize passionSize;
    private String additionalWriting;
    private String openChatUrl;

    private List<GetRequirementOutput> requirementList;
}
