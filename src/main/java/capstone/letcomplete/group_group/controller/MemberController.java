package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.dto.output.SignupOutput;
import capstone.letcomplete.group_group.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name="Member", description = "Member 관련 API")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "Signup Start", description = "일반 회원(Member)에 대한 회원가입을 인증메일을 요청하는 API")
    public void signupStart(@RequestBody @Valid SignupMemberInput input) throws MessagingException {
        memberService.signupStart(input);
    }

    @GetMapping("signup/complete")
    @Operation(summary = "Signup Complete", description = "회원가입 인증메일을 클릭했을때 실행되는 API")
    public SignupOutput signupComplete(
            @RequestParam(name="email") String email,
            @RequestParam(name="certificationNumber") String certificationNumber
    )  {
        return new SignupOutput(memberService.signupComplete(email, certificationNumber));
    }

}
