package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.enumtype.AccountType;
import capstone.letcomplete.group_group.entity.enumtype.MemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtClaimsDataDto {
    private Long id;
    private String email;
    private AccountType role;
}
