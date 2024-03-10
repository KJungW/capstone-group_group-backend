package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.MemberRoleType;
import lombok.Getter;

@Getter
public class MemberInfoDto {
    private Long id;
    private String email;
    private String password;
    private MemberRoleType role;

    public MemberInfoDto (Member member) {
        id = member.getId();
        email = member.getEmail();
        password = member.getPassword();
        role = member.getRole();
    }
}
