package capstone.letcomplete.group_group.entity.valuetype;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Requirement {
    private String id;
    private String title;
    private RequirementResultType resultType;
}
