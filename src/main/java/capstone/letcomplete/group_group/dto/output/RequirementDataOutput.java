package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.RequirementData;
import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequirementDataOutput {
    private String requirementId;
    private String requirementTitle;
    private RequirementResultType resultType;
    private String content;

    public RequirementDataOutput(RequirementData dto) {
        this.requirementId = dto.getRequirementId();
        this.resultType = dto.getResultType();
        this.content = dto.getContent();
        this.requirementTitle = dto.getRequirementTitle();
    }

}
