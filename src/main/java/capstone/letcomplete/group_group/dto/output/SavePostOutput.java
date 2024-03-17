package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SavePostOutput {
    @Schema(description = "저장완료된 모집글 ID")
    private Long savedPostId;
}
