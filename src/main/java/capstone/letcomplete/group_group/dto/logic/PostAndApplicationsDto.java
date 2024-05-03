package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostAndApplicationsDto {
    List<PostAndApplicationsOverviewDto> postAndApplicationsOverviews;
    private int totalPages;
    private int currentPageNumber;
    private boolean isLastPage;
    private boolean isFirstPage;
}
