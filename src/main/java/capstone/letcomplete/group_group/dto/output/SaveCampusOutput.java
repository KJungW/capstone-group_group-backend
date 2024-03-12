package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveCampusOutput {
    @Schema(description = "등록 완료된 캠퍼스 ID")
    private Long campusId;
}
