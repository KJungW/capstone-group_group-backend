package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.ManagerRoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupMangerInput {
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank
    @Schema(description = "생성할 관리자의 이메일")
    private String email;
    @NotBlank
    @Schema(description = "생성할 관리자의 비밀번호")
    private String password;
    @NotBlank
    @Schema(description = "생성할 관리자의 닉네임")
    private String nickName;
    @NotNull
    @Schema(description = "생성할 관리자의 권한")
    private ManagerRoleType role;
}
