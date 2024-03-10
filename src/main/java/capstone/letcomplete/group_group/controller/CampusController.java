package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.output.SaveCampusOutput;
import capstone.letcomplete.group_group.service.CampusService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campus")
@RequiredArgsConstructor
public class CampusController {
    private final CampusService campusService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    public SaveCampusOutput save(
            @NotBlank @RequestParam(required = true) String campusName
    ){
        return new SaveCampusOutput(campusService.save(campusName));
    }

}
