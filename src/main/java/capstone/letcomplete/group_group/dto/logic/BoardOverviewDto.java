package capstone.letcomplete.group_group.dto.logic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardOverviewDto {
    @Schema(description = "게시판 ID")
    private Long boardId;
    @Schema(description = "게시판 제목")
    private String boardTitle;
}
