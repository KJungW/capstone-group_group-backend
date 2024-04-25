package capstone.letcomplete.group_group.entity.valuetype;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.Getter;

@Getter
public class TextResult extends RequirementResult{
    private String title;

    public TextResult(String requirementId, String title) {
        super(requirementId, RequirementResultType.TEXT);
        this.title = title;
    }
}
