package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.dto.output.LoginMemberOutput;
import capstone.letcomplete.group_group.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name="Auth", description = "일반회원 인증과 인가 API")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    @Operation(summary = "Member Login", description = "일반 회원 로그인")
    public LoginMemberOutput login(@Valid @RequestBody LoginInput loginInput) {

        return authService.login(loginInput);
    }
    @PostMapping("logout")
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Member Logout", description = "일반 회원 로그아웃")
    public String logout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        authService.logout(memberId);
        return "ok";
    }
}
