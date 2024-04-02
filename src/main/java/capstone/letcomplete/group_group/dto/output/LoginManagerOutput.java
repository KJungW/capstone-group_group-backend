package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.entity.Manager;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginManagerOutput {

    @Schema(description = "로그인한 관리자의 ID")
    private Long id;

    @Schema(description = "로그인한 관리자의 email")
    private String email;

    @Schema(description = "로그인한 관리자의 nickName")
    private String nickName;

    @Schema(description = "jwt 토큰")
    private String jwtToken;

    public LoginManagerOutput(Long id, String email, String nickName, String jwtToken) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.jwtToken = jwtToken;
    }

    public LoginManagerOutput(Manager manager, String jwtToken) {
        this.id = manager.getId();
        this.email = manager.getEmail();
        this.nickName = manager.getNickName();
        this.jwtToken = jwtToken;
    }
}
