package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.DisabledApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class DisabledAppPreviewsInPageDto {
    private List<DisabledAppPreviewDto> disabledApplicationPreviewList = new ArrayList<>();
    private int totalPages;
    private int currentPageNumber;
    private boolean isLastPage;
    private boolean isFirstPage;

    public DisabledAppPreviewsInPageDto (Page<DisabledApplication> dto) {
        this.disabledApplicationPreviewList = dto.getContent().stream().map(DisabledAppPreviewDto::new).toList();
        this.totalPages = dto.getTotalPages();
        this.currentPageNumber = dto.getNumber();
        this.isLastPage = dto.isLast();
        this.isFirstPage = dto.isFirst();
    }
}
