package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationOverviewDto {
    private Long postId;
    private Long applicationId;
    private String applicantNickName;
    private Boolean applicationIsPassed;
}
