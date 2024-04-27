package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.ApplicationOverviewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationOverViewOutput {
    private Long applicationId;
    private String applicantNickName;
    private Boolean applicationIsPassed;

    public ApplicationOverViewOutput (ApplicationOverviewDto dto) {
        this.applicationId = dto.getApplicationId();
        this.applicantNickName = dto.getApplicantNickName();
        this.applicationIsPassed = dto.getApplicationIsPassed();
    }
}
