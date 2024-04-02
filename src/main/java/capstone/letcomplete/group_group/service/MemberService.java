package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.dto.logic.JoinCache;
import capstone.letcomplete.group_group.entity.Campus;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.MemberRoleType;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.exception.SignupLogicException;
import capstone.letcomplete.group_group.repository.JoinCacheRedisRepository;
import capstone.letcomplete.group_group.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@PropertySource("classpath:application.yaml")
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final CampusService campusService;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;
    private final JoinCacheRedisRepository joinCacheRedisRepository;
    private final Environment env;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional()
    public void signupStart(SignupMemberInput input) throws MessagingException {
        // 입력된 회원정보에 대한 검증
        validateEmail(input.getEmail());
        campusService.checkCampusExistence(input.getCampusId());

        try {
            // 인증번호 생성
            String certificationNumber = makeCertificationNumber();
            // Redis에 임시 회원가입정보 저장
            joinCacheRedisRepository.saveJoinCache(new JoinCache(certificationNumber, input));
            // 메일 내용 전송
            String title = "[Group-Group] 회원가입 이메일 인증";
            String content = makeSignupCompleteMailContent(certificationNumber, input.getEmail());
            mailSendService.sendEmail(input.getEmail(), title, content);

        } catch (NoSuchAlgorithmException e) {
            log.error("인증번호 생성 중 예외발생 : ", e);
            throw new SignupLogicException("인증번호 생성 중 예외발생");
        } catch (JsonProcessingException e) {
            log.error("회원가입 정보를 캐시에 저장 중 JSON변환 예외 발생 : ", e);
            throw new SignupLogicException("회원가입 정보를 캐시에 저장 중 JSON변환 예외 발생");
        }
    }

    @Transactional()
    public Long signupComplete(String email, String certificationNumber) {
        try{
            checkSignupDataExistenceInCache(email);
            JoinCache joinCache = joinCacheRedisRepository.getJoinCache(email);
            validateCertificationNumber(certificationNumber, joinCache.getCertificationNumber());
            return saveMember(joinCache.getSignupMemberInput()).getId();
        } catch (JsonProcessingException e) {
            log.error("캐시에 저장된 회원가입 정보를 가져올때, JSON변환 예외 발생 : ", e);
            throw new SignupLogicException("캐시에 저장된 회원가입 정보를 가져올때, JSON변환 예외 발생");
        }
    }

    private void checkSignupDataExistenceInCache(String email) {
        if(!joinCacheRedisRepository.hasKey(email))
            throw new DataNotFoundException("Redis에 이메일에 해당하는 정보가 존재하지 않습니다.");
    }

    private void validateCertificationNumber(String newNumber, String originNumber) {
        if(!newNumber.equals(originNumber)){
            throw new InvalidInputException("잘못된 인증번호가 입력되었습니다.");
        }
    }

    @Transactional()
    public Member saveMember(SignupMemberInput input) {
        String email = input.getEmail();
        String password = passwordEncoder.encode(input.getPassword());
        String nickName = input.getNickName();
        Campus campus = campusService.findById(input.getCampusId());

        validateEmail(email);

        return  memberRepository.save(Member.makeMember(email, password, nickName, MemberRoleType.ME_COMMON, campus));
    }

    private String makeCertificationNumber() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append(secureRandom.nextInt(10));
        }
        return result.toString();
    }

    private String makeSignupCompleteMailContent(String certificationNumber, String email) {
        String baseURL = env.getProperty("project_info.baseURL");
        String certificationNumberProperty = "certificationNumber="+certificationNumber;
        String emailProperty = "email="+email;
        String signupMail = String.format(
                "%s/member/signup/complete?%s&%s",
                baseURL, certificationNumberProperty, emailProperty
        );

        return "<!DOCTYPE>" +
                "<html>" +
                    "<body>" +
                        "<h3>아래의 링크에 들어가 회원가입을 완료해주세요.</h3>" +
                        "<a href="+signupMail+">회원가입 완료</a>" +
                    "</body>" +
                "</html>";
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
