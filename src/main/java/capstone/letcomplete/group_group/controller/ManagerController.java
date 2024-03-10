package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.dto.input.SignupMangerInput;
import capstone.letcomplete.group_group.dto.output.SignupOutput;
import capstone.letcomplete.group_group.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService mangerService;

    @PostMapping("/signup")
    public SignupOutput signup(@RequestBody @Valid SignupMangerInput input){
        return new SignupOutput(mangerService.signup(input));
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginInput loginInput) {
        return mangerService.login(loginInput);
    }
}
