package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostOverViewsInBoardDto {
    private List<PostOverViewDto> contents;
    private int totalPages;
    private int currentPageNumber;
    private boolean isLastPage;
    private boolean isFirstPage;
}
