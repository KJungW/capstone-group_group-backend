package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.entity.Campus;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.MemberRoleType;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
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
    public Long signup(SignupMemberInput input) {
        // 회원가입 정보 검증 및 가공
        String email = input.getEmail();
        validateEmail(email);
        String password = passwordEncoder.encode(input.getPassword());
        String nickName = input.getNickName();
        Campus campus = campusService.findById(input.getCampusId());

        // 회원정보 저장
        Member newMember = memberRepository.save(
                Member.makeMember(email, password, nickName, MemberRoleType.COMMON, campus));
        return newMember.getId();
    }

    private void validateEmail(String email) {
        if(memberRepository.findByEmail(email).isPresent())
            throw new InvalidInputException("이미 존재하는 이메일입니다.");
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("이메일에 해당하는 회원이 없습니다."));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("id에 해당하는 회원이 없습니다."));
    }
}
