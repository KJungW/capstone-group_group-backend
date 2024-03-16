package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.ManagerRoleType;
import capstone.letcomplete.group_group.value.RegularExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupMangerInput {
    @NotBlank
    @Email(regexp = RegularExpression.EMAIL_RE)
    @Schema(description = "생성할 관리자의 이메일")
    private String email;
    @NotBlank
    @Pattern(regexp = RegularExpression.PASSWORD_RE)
    @Schema(description = "생성할 관리자의 비밀번호")
    private String password;
    @NotBlank
    @Schema(description = "생성할 관리자의 닉네임")
    private String nickName;
    @NotNull
    @Schema(description = "생성할 관리자의 권한")
    private ManagerRoleType role;
}
