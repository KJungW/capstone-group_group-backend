package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.ApplicationDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class GetApplicationDetailOutput {
    private Long applicationId;
    private Long postId;
    private Long applicant;
    private boolean isPassed;
    private List<RequirementDataOutput> requirementDataList = new ArrayList<>();

    public GetApplicationDetailOutput(ApplicationDetailDto dto) {
        this.applicationId = dto.getApplicationId();
        this.postId = dto.getPostId();
        this.applicant = dto.getApplicant();
        this.isPassed = dto.isPassed();
        this.requirementDataList = dto.getRequirementDataList().stream().map(requirementData -> new RequirementDataOutput(requirementData)).toList();
    }
}
