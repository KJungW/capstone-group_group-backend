package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.FindMemberByTokenInput;
import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.dto.logic.ApplicationsByMemberDto;
import capstone.letcomplete.group_group.dto.logic.DisabledAppPreviewsInPageDto;
import capstone.letcomplete.group_group.dto.logic.PostAndApplicationsDto;
import capstone.letcomplete.group_group.dto.output.*;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
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
    private final DisabledApplicationService disabledApplicationService;
    private final MemberAndPostService memberAndPostService;

    @PostMapping("/signup")
    @Operation(summary = "Signup Start", description = "일반 회원(Member)에 대한 회원가입을 인증메일을 요청하는 API")
    public void signupStart(@RequestBody @Valid SignupMemberInput input) {
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

    @GetMapping("signup/nickname")
    @Operation(summary = "Check Nickname Availability", description = "닉네임 사용 가능여부 확인")
    public String checkNicknameAvailability(
            @Length(min=1, max = 20) @RequestParam(name="nickName") String nickName
    ) {
        memberService.checkNickNameAvailability(nickName);
        return "ok";
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
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
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
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
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

    @GetMapping("/disabled-application")
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Get Disabled Application List By Member", description = "회원ID를 통해 회원이 작성한 신청들 중 비활성화된 신청리스트 조회")
    public GetDisabledApplicationsByMemberOutput getDisabledApplicationByMember(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        DisabledAppPreviewsInPageDto searchResult = disabledApplicationService.findPreviewsInPageByMember(memberId, pageNumber, pageSize);
        return new GetDisabledApplicationsByMemberOutput(searchResult);
    }


    @GetMapping("/post")
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Get Post Detail written by member", description = "자신이 작성한 모집글에 대한 세부정보를 조회하는 API")
    public GetPostDetailByMemberOutput getPostDetailByMember(
            @Schema(description = "조회할 모집글 ID")
            @RequestParam(name = "postId") Long postId
    ) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        return postUsageService.getPostDetailByMember(postId, memberId);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Get Post Detail written by member", description = "자신이 작성한 모집글에 대한 세부정보를 조회하는 API")
    public String deleteMember() throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        memberAndPostService.deleteMember(memberId);
        return "ok";
    }

}
