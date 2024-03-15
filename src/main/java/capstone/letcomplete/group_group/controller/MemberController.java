package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.dto.output.SignupOutput;
import capstone.letcomplete.group_group.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Create New Member", description = "일반 회원(Member)에 대한 회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request(파라미터 유효성, 이메일 중복, 캠퍼스ID 유효성 등을 확인")
    })
    public void signupStart(@RequestBody @Valid SignupMemberInput input) throws MessagingException {
        memberService.signupStart(input);
    }

    @GetMapping("signup/complete")
    public SignupOutput signupComplete(
            @RequestParam(name="email") String email,
            @RequestParam(name="certificationNumber") String certificationNumber
    )  {
        return new SignupOutput(memberService.signupComplete(email, certificationNumber));
    }




}
