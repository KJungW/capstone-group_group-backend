package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinCache {
    private String certificationNumber;
    private SignupMemberInput signupMemberInput;
}
