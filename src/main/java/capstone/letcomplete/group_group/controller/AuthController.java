package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public String login(@Valid @RequestBody LoginInput loginInput) {
        return authService.login(loginInput);
    }
}
