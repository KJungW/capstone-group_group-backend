package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.ApplicationsByMemberDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GetApplicationsByMemberOutput {
    @Schema(description = "신청과 해당 신청의 결과 리스트")
    private List<ApplicationAndResultOutput> applicationAndResult = new ArrayList<>();
    @Schema(description = "슬라이스 번호")
    private int sliceNum;
    @Schema(description = "첫 슬라이스인지 여부")
    private boolean isFirst;
    @Schema(description = "마지막 슬라이스인지 여부")
    private boolean isLast;
    @Schema(description = "다음 슬라이스가 있는지 여부")
    private boolean hasNext;

    public GetApplicationsByMemberOutput(ApplicationsByMemberDto dto) {
        this.applicationAndResult = dto.getApplicationAndResultList().stream()
                .map(ApplicationAndResultOutput::new).collect(Collectors.toList());
        this.sliceNum = dto.getSliceNum();
        this.isFirst = dto.isFirst();
        this.isLast = dto.isLast();
        this.hasNext = dto.isHasNext();
    }
}
