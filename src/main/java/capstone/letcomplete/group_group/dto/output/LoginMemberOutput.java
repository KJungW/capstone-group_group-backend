package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginMemberOutput {
    @Schema(description = "로그인한 회원의 ID")
    private Long id;
    @Schema(description = "로그인한 회원의 email")
    private String email;
    @Schema(description = "로그인한 회원의 nickName")
    private String nickName;
    @Schema(description = "jwt 토큰")
    private String jwtToken;
    @Schema(description = "로그인한 회원이 속한 대학교ID")
    private Long campusId;

    public LoginMemberOutput(Long id, String email, String nickName, String jwtToken, Long campusId) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.jwtToken = jwtToken;
        this.campusId = campusId;
    }

    public LoginMemberOutput(Member member, String jwtToken) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickName = member.getNickName();
        this.jwtToken = jwtToken;
        this.campusId = member.getCampus().getId();
    }
}
