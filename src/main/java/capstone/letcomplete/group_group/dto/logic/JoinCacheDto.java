package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinCacheDto {
    private String certificationNumber;
    private SignupMemberInput signupMemberInput;
}
