package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.FindMemberByTokenInput;
import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.dto.logic.ApplicationsByMemberDto;
import capstone.letcomplete.group_group.dto.logic.PostAndApplicationsDto;
import capstone.letcomplete.group_group.dto.output.FindMemberByTokenOutput;
import capstone.letcomplete.group_group.dto.output.GetApplicationsByMemberOutput;
import capstone.letcomplete.group_group.dto.output.GetPostAndApplicationsByMemberOutput;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.service.ApplicationService;
import capstone.letcomplete.group_group.service.MemberService;
import capstone.letcomplete.group_group.service.PostUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name="Member", description = "Member 관련 API")
public class MemberController {
    private final MemberService memberService;
    private final PostUsageService postUsageService;
    private final ApplicationService applicationService;

    @PostMapping("/signup")
    @Operation(summary = "Signup Start", description = "일반 회원(Member)에 대한 회원가입을 인증메일을 요청하는 API")
    public void signupStart(@RequestBody @Valid SignupMemberInput input) throws MessagingException {
        memberService.signupStart(input);
    }

    @GetMapping("signup/complete")
    @Operation(summary = "Signup Complete", description = "회원가입 인증메일을 클릭했을때 실행되는 API")
    public String signupComplete(
            @RequestParam(name="email") String email,
            @RequestParam(name="certificationNumber") String certificationNumber
    )  {
        memberService.signupComplete(email, certificationNumber);
        return "회원가입이 완료되었습니다. 홈페이지로 돌아가서 로그인을 해주세요";
    }

    @PostMapping("/token")
    @Operation(summary = "Find Member By Token", description = "토큰을 통해 Member정보를 조회하는 API")
    public FindMemberByTokenOutput findMemberByToken(
            @Valid @RequestBody FindMemberByTokenInput input
    ) {
        Member member = memberService.findByToken(input.getToken());
        return new FindMemberByTokenOutput(
                member.getId(), member.getEmail(),
                member.getNickName(), input.getToken(),
                member.getCampus().getId());
    }

    @GetMapping("/posts")
    @PreAuthorize("hasAnyRole('ROLE_ME_COMMON', 'ROLE_MG_COMMON')")
    @Operation(summary = "Get PostOverView List By MemberID", description = "회원ID를 통해 회원이 작성한 모집글 리스트를 조회")
    public GetPostAndApplicationsByMemberOutput getPostAndApplicationByMember(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        PostAndApplicationsDto postAndApplicationsByMember = postUsageService.findPostAndApplicationsByMember(pageNumber, pageSize, memberId);
        return new GetPostAndApplicationsByMemberOutput(postAndApplicationsByMember);
    }
    
    @GetMapping("/applications")
    @PreAuthorize("hasAnyRole('ROLE_ME_COMMON', 'ROLE_MG_COMMON')")
    @Operation(summary = "Get Application List By Member", description = "회원ID를 통해 회원이 작성한 신청리스트 조회")
    public GetApplicationsByMemberOutput getApplicationsByMember(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        ApplicationsByMemberDto result = applicationService.findApplicationsByMember(pageNumber, pageSize, memberId);
        return new GetApplicationsByMemberOutput(result);
    }

}
