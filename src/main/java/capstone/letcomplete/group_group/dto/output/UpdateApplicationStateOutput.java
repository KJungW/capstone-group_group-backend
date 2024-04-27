package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateApplicationStateOutput {
    @Schema(description = "수정완료된 애플리케이션 ID")
    private Long applicationId;
}
