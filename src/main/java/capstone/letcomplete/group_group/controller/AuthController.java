package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public String login(@Valid @RequestBody LoginInput loginInput) {
        return authService.login(loginInput);
    }
}
