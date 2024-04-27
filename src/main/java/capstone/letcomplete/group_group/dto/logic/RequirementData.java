package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequirementData {
    private String requirementId;
    private String requirementTitle;
    private RequirementResultType resultType;
    private String content;
}