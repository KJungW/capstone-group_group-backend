package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.output.GetPostsInBoardOutput;
import capstone.letcomplete.group_group.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name="Board", description = "Board 관련 API")
public class BoardController {
    private final PostService postService;

    @GetMapping("/posts")
    @Operation(summary = "Get Posts In Board", description = "게시판에 속한 모집글을 조회")
    public GetPostsInBoardOutput getPostsInBoard(
            @Schema(description = "조회할 페이지 번호") @Min(value = 0) @RequestParam("pageNumber") int pageNumber,
            @Schema(description = "페이지 사이즈") @Min(value = 1) @RequestParam("pageSize") int pageSize,
            @Schema(description = "조회할 페이지가 속한 게시판 ID") @Min(value = 0) @RequestParam("boardId") Long boardId
    ) {
        return postService.findPostOverViewInBoard(pageNumber, pageSize, boardId);
    }
}
