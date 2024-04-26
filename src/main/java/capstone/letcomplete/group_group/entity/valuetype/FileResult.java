package capstone.letcomplete.group_group.entity.valuetype;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public static FileResult createFileResultForJson(
            @JsonProperty("requirementId") String requirementId,
            @JsonProperty("externalName") String externalName,
            @JsonProperty("internalName") String internalName,
            @JsonProperty("url") String url){
        return new FileResult(requirementId, externalName, internalName, url);
    }
}
