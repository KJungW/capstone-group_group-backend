package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.DisabledAppPreviewsInPageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GetDisabledApplicationsByMemberOutput {
    @Schema(description = "신청과 해당 신청의 결과 리스트")
    private List<DisabledAppPreviewOutput> disabledAppPreviewOutputs = new ArrayList<>();
    @Schema(description = "전체 페이지수")
    private int totalPages;
    @Schema(description = "현재 페이지번호")
    private int currentPageNumber;
    @Schema(description = "현재 페이지가 마지막 페이지인지 여부")
    private boolean isLastPage;
    @Schema(description = "현재 페이지가 첫 페이지인지 여부")
    private boolean isFirstPage;

    public GetDisabledApplicationsByMemberOutput (DisabledAppPreviewsInPageDto dto) {
        this.disabledAppPreviewOutputs = dto.getDisabledApplicationPreviewList().stream().map(DisabledAppPreviewOutput::new).collect(Collectors.toList());
        this.totalPages = dto.getTotalPages();
        this.currentPageNumber = dto.getCurrentPageNumber();
        this.isLastPage = dto.isLastPage();
        this.isFirstPage = dto.isFirstPage();
    }

}
