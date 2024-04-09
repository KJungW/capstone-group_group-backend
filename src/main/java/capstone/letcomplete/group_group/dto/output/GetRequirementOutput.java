package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetRequirementOutput {
    private String id;
    private String title;
    private RequirementResultType resultType;
}
