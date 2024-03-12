package capstone.letcomplete.group_group.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupMemberInput {
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank
    @Schema(description = "회원가입할 유저의 이메일 (중복허용x) (이메일 형식 : ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$)")
    private String email;
    @NotBlank
    @Schema(description = "회원가입할 유저의 비밀번호")
    private String password;
    @NotBlank
    @Schema(description = "회원가입할 유저의 닉네임")
    private String nickName;
    @NotNull
    @Positive
    @Schema(description = "회원가입할 유저가 속한 캠퍼스 ID")
    private Long campusId;
}
