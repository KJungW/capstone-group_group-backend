package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.value.RegularExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginInput {
    @NotBlank
    @Email(regexp = RegularExpression.EMAIL_RE)
    @Schema(description = "회원의 email")
    private String email;
    @NotBlank
    @Pattern(regexp = RegularExpression.PASSWORD_RE)
    @Schema(description = "회원의 비밀번호")
    private String password;
}
