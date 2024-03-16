package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.LoginInput;
import capstone.letcomplete.group_group.dto.input.SignupMangerInput;
import capstone.letcomplete.group_group.dto.output.SignupOutput;
import capstone.letcomplete.group_group.service.ManagerService;
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
@RequestMapping("/manager")
@RequiredArgsConstructor
@Tag(name = "Manager", description = "관지자 관련 모든 API")
public class ManagerController {
    private final ManagerService mangerService;

    @PostMapping("/login")
    @Operation(summary = "Manager Login", description = "관리자 로그인")
    public String login(@Valid @RequestBody LoginInput loginInput) {
        return mangerService.login(loginInput);
    }
}
