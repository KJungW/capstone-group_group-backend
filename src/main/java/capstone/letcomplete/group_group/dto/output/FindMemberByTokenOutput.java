package capstone.letcomplete.group_group.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindMemberByTokenOutput {
    @Schema(description = "회원의 ID")
    private Long id;
    @Schema(description = "회원의 email")
    private String email;
    @Schema(description = "회원의 nickName")
    private String nickName;
    @Schema(description = "jwt 토큰")
    private String jwtToken;
    @Schema(description = "회원이 속한 대학교ID")
    private Long campusId;
}
