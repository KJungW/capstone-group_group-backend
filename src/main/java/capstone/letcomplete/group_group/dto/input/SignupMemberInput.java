package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.value.RegularExpression;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupMemberInput {
    @NotBlank
    @Email(regexp = RegularExpression.EMAIL_RE)
    @Schema(description = "회원가입할 유저의 이메일")
    private String email;
    @NotBlank
    @Pattern(regexp = RegularExpression.PASSWORD_RE)
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
