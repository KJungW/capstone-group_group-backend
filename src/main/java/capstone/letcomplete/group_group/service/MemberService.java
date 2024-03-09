package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SignupInput;
import capstone.letcomplete.group_group.entity.Campus;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final CampusService campusService;
    private final PasswordEncoder passwordEncoder;

    @Transactional()
    public Long signup(SignupInput input) {
        // 회원가입 정보 검증 및 가공
        String email = input.getEmail();
        validateEmail(email);
        String password = passwordEncoder.encode(input.getPassword());
        Campus campus = campusService.findById(input.getCampusId());

        // 회원정보 저장
        Member newMember = memberRepository.save(
                Member.makeMember(email, password, campus));
        return newMember.getId();
    }

    private void validateEmail(String email) {
        memberRepository.findByEmail(email).orElseThrow(
                ()->new InvalidInputException("이미 존재하는 이메일입니다."));
    }
}
