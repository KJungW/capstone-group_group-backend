package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupOutput {
    @Schema(description = "회원가입이 완료된 일반회원의 ID")
    private Long id;
}
