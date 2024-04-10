package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveApplicationOutput {
    @Schema(description = "등록 완료된 신청의 ID")
    private Long applicationId;
}
