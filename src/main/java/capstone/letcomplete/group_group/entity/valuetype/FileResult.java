package capstone.letcomplete.group_group.entity.valuetype;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.Getter;

@Getter
public class FileResult extends RequirementResult{
    private String externalName;
    private String internalName;

    public FileResult(RequirementResultType type, String externalName, String internalName) {
        super(type);
        this.externalName = externalName;
        this.internalName = internalName;
    }
}
