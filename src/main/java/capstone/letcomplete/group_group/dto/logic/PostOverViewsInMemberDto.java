package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostOverViewsInMemberDto {
    private List<PostOverViewDto> contents;
    private int sliceNum;
    boolean isFirst;
    boolean isLast;
    boolean hasNext;
}
