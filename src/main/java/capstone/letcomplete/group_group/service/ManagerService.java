package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.dto.input.SignupMangerInput;
import capstone.letcomplete.group_group.dto.logic.JwtClaimsDataDto;
import capstone.letcomplete.group_group.dto.logic.MemberInfoDto;
import capstone.letcomplete.group_group.entity.Manager;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.AccountType;
import capstone.letcomplete.group_group.entity.enumtype.ManagerRoleType;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.repository.ManagerRepository;
import capstone.letcomplete.group_group.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional()
    public Long signup(SignupMangerInput input) {
        // 회원가입 정보 검증 및 가공
        String email = input.getEmail();
        validateEmail(email);
        String password = passwordEncoder.encode(input.getPassword());
        String nickName = input.getNickName();
        ManagerRoleType role = input.getRole();

        // 관리자정보 저장
        Manager newManager = managerRepository.save(
                Manager.makeManager(email, password, nickName, role));
        return newManager.getId();
    }

    public String login(LoginInput loginInput) {
        // 이메일을 가지고 해당하는 회원정보를 가져온다.
        Manager manager = findByEmail(loginInput.getEmail());

        // 비밀번호 검증
        if(!passwordEncoder.matches(loginInput.getPassword(), manager.getPassword())) {
            throw new InvalidInputException("잘못된 비밀번호");
        }

        // Access Token 생성
        return jwtUtil.makeAccessToken(new JwtClaimsDataDto(
                manager.getId(), manager.getEmail(), AccountType.MANAGER
        ));
    }

    private void validateEmail(String email) {
        if(managerRepository.findByEmail(email).isPresent())
            throw new InvalidInputException("이미 존재하는 이메일입니다.");
    }


    public Manager findByEmail(String email) {
        return managerRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("이메일에 해당하는 회원이 없습니다."));
    }
//
//    public Member findById(Long id) {
//        return managerRepository.findById(id).orElseThrow(
//                () -> new DataNotFoundException("id에 해당하는 회원이 없습니다."));
//    }
}
