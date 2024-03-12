package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.output.SaveCampusOutput;
import capstone.letcomplete.group_group.service.CampusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Campus", description = "Campus 관련 API")
public class CampusController {
    private final CampusService campusService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Add New Campus", description = "새로운 캠퍼스를 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request(파라미터를 제대로 입력했는지 확인, 학교이름 중복등록 확인)")
    })
    public SaveCampusOutput save(
            @Parameter(description = "등록한 캠퍼스 이름")
            @NotBlank @RequestParam(required = true) String campusName
    ){
        return new SaveCampusOutput(campusService.save(campusName));
    }

}
