package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.RequirementDataDto;
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
    private String fileName;

    public RequirementDataOutput(RequirementDataDto dto) {
        this.requirementId = dto.getRequirementId();
        this.resultType = dto.getResultType();
        this.content = dto.getContent();
        this.requirementTitle = dto.getRequirementTitle();
        this.fileName = dto.getInternalFileName();
    }

}
