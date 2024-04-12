package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetDefaultCampusIdOutput {
    @Schema(description = "디폴트 캠퍼스의 ID")
    private Long defaultCampusId;
}
