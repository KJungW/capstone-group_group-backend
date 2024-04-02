package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.dto.logic.JwtClaimsDataDto;
import capstone.letcomplete.group_group.dto.logic.MemberInfoDto;
import capstone.letcomplete.group_group.dto.output.LoginMemberOutput;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.AccountType;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public LoginMemberOutput login(LoginInput loginInput) {
        // 이메일을 가지고 해당하는 회원정보를 가져온다.
        Member findMember = memberService.findByEmail(loginInput.getEmail());

        // 비밀번호 검증
        if(!encoder.matches(loginInput.getPassword(), findMember.getPassword())) {
            throw new InvalidInputException("잘못된 비밀번호");
        }

        // Access Token 생성
        String jwtToken = jwtUtil.makeAccessToken(new JwtClaimsDataDto(
                findMember.getId(), findMember.getEmail(), AccountType.MEMBER));

        return new LoginMemberOutput(findMember, jwtToken);
    }
}
