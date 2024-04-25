package capstone.letcomplete.group_group.entity.valuetype;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.Getter;

@Getter
public class FileResult extends RequirementResult{
    private String externalName;
    private String internalName;
    private String url;

    public FileResult(String requirementId, String externalName, String internalName, String url) {
        super(requirementId, RequirementResultType.FILE);
        this.externalName = externalName;
        this.internalName = internalName;
        this.url = url;
    }
}
