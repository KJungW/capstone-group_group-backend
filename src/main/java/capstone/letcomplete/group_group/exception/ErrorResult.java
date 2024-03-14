package capstone.letcomplete.group_group.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {
    @Schema(description = "상태코드")
    private String code;
    @Schema(description = "메세지")
    private String message;
}
