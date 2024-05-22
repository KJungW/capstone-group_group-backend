package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.PostOverViewDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchPostInBoardByTitleOutput {
    @Schema(description = "게시판 이름")
    private String boardTitle;
    @Schema(description = "조회한 게시글 목록")
    private List<PostOverViewDto> contents;
    @Schema(description = "전체 페이지수")
    private int totalPages;
    @Schema(description = "현재 페이지번호")
    private int currentPageNumber;
    @Schema(description = "현재 페이지가 마지막 페이지인지 여부")
    private boolean isLastPage;
    @Schema(description = "현재 페이지가 첫 페이지인지 여부")
    private boolean isFirstPage;

    public SearchPostInBoardByTitleOutput(String boardTitle, Page<PostOverViewDto> pageData) {
        this.boardTitle = boardTitle;
        this.contents = pageData.getContent();
        this.totalPages = pageData.getTotalPages();
        this.currentPageNumber = pageData.getNumber();
        this.isLastPage = pageData.isLast();
        this.isFirstPage = pageData.isFirst();
    }
}
