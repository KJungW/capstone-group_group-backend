package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.valuetype.FileResult;
import capstone.letcomplete.group_group.entity.valuetype.TextResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllRequirementResultsInJson {
    private List<TextResult> textResults;
    private List<FileResult> fileResults;
}
