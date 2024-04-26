package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicationDetailDto {
    private Long applicationId;
    private Long postId;
    private Long applicant;
    private boolean isPassed;
    private List<RequirementData> requirementDataList = new ArrayList<>();
}
