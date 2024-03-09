package capstone.letcomplete.group_group.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupInput {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    @Positive
    private Long campusId;
}
