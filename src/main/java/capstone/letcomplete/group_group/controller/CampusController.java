package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.output.GetBoardListInCampusOutput;
import capstone.letcomplete.group_group.dto.output.SaveCampusOutput;
import capstone.letcomplete.group_group.service.BoardService;
import capstone.letcomplete.group_group.service.CampusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campus")
@RequiredArgsConstructor
@Tag(name="Campus", description = "Campus 관련 API")
public class CampusController {
    private final CampusService campusService;
    private final BoardService boardService;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_MG_COMMON')")
    @Operation(summary = "Add New Campus", description = "새로운 캠퍼스를 등록")
    public SaveCampusOutput save(
            @Parameter(description = "등록한 캠퍼스 이름")
            @NotBlank @RequestParam(required = true) String campusName
    ){
        return new SaveCampusOutput(campusService.save(campusName));
    }

    @GetMapping("/boards")
    @Operation(summary = "Get Boards In Campus", description = "캠퍼스에 속하는 모든 게시판 조회")
    public GetBoardListInCampusOutput getBoardListInCampus(
            @Schema(description = "게시판들을 조회할때 사용할 캠퍼스ID") @Min(value = 0) @RequestParam("campusId") Long campusId
    ) {
        return boardService.findByCampus(campusId);
    }

}
