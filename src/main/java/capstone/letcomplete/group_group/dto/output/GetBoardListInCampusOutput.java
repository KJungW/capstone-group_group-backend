package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.BoardOverviewDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetBoardListInCampusOutput {
    @Schema(description = "캠퍼스 id")
    private Long campusId;
    @Schema(description = "캠퍼스에 속한 모든 게시판")
    private List<BoardOverviewDto> content;
}
