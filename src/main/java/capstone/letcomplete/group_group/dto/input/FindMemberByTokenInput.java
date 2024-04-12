package capstone.letcomplete.group_group.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindMemberByTokenInput {
    @NotBlank
    @Schema(description = "유효성을 검사할 jwt토큰")
    private String token;
}
