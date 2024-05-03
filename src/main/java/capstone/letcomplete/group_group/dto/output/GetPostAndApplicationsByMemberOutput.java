package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.PostAndApplicationsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GetPostAndApplicationsByMemberOutput {
    List<PostAndApplicationsOutput> postAndApplicationsOverviews;
    @Schema(description = "전체 페이지수")
    private int totalPages;
    @Schema(description = "현재 페이지번호")
    private int currentPageNumber;
    @Schema(description = "현재 페이지가 마지막 페이지인지 여부")
    private boolean isLastPage;
    @Schema(description = "현재 페이지가 첫 페이지인지 여부")
    private boolean isFirstPage;

    public GetPostAndApplicationsByMemberOutput(PostAndApplicationsDto dto) {
        this.postAndApplicationsOverviews = dto.getPostAndApplicationsOverviews().stream().map(PostAndApplicationsOutput::new).collect(Collectors.toList());
        this.totalPages  = dto.getTotalPages();
        this.currentPageNumber = dto.getCurrentPageNumber();
        this.isLastPage = dto.isLastPage();
        this.isFirstPage = dto.isFirstPage();
    }

}
