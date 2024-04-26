package capstone.letcomplete.group_group.entity.valuetype;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TextResult extends RequirementResult{
    private String content;

    public TextResult(String requirementId, String content) {
        super(requirementId, RequirementResultType.TEXT);
        this.content = content;
    }

    @JsonCreator
    public static TextResult createTextResultForJson(
            @JsonProperty("requirementId") String requirementId,
            @JsonProperty("content") String content
    ) {
        return new TextResult(requirementId, content);
    }


}
