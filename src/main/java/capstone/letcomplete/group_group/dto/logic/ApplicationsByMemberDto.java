package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicationsByMemberDto {
    private List<ApplicationAndResultDto> applicationAndResultList = new ArrayList<>();
    private int totalPages;
    private int currentPageNumber;
    private boolean isLastPage;
    private boolean isFirstPage;
}
