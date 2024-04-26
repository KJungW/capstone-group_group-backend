package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.SaveApplicationInput;
import capstone.letcomplete.group_group.dto.logic.ApplicationDetailDto;
import capstone.letcomplete.group_group.dto.output.GetApplicationDetailOutput;
import capstone.letcomplete.group_group.dto.output.SaveApplicationOutput;
import capstone.letcomplete.group_group.service.ApplicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Tag(name="Application", description = "신청 관련 API")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ME_COMMON', 'ROLE_MG_COMMON')")
    @Operation(summary = "Add Application", description = "모집글에 대한 신청")
    public SaveApplicationOutput saveApplication(
            @RequestPart("applicationData") SaveApplicationInput input,
            @RequestPart("filesInApplication") List<MultipartFile> inputFiles
    ) throws IOException {
        return new SaveApplicationOutput(applicationService.saveApplication(input, inputFiles));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ME_COMMON', 'ROLE_MG_COMMON')")
    @Operation(summary = "GET APPLICATION DETAIL", description = "신청 세부정보 조회")
    public GetApplicationDetailOutput getApplicationDetail(
            @RequestParam("applicationId") Long applicationId
    ) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        ApplicationDetailDto applicationDetail = applicationService.findApplicationDetail(memberId, applicationId);
        return new GetApplicationDetailOutput(applicationDetail);
    }
}