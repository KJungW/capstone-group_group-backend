package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.SignupInput;
import capstone.letcomplete.group_group.dto.output.SignupOutput;
import capstone.letcomplete.group_group.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public SignupOutput signup(@RequestBody @Valid SignupInput input){
        return new SignupOutput(memberService.signup(input));
    }


}
