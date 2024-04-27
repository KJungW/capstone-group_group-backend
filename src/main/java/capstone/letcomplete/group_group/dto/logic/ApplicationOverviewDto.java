package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationOverviewDto {
    private Long postId;
    private Long applicationId;
    private String applicantNickName;
    private ApplicationState applicationIsPassed;
}
