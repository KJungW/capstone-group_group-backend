package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.dto.output.GetPostDetailOutput;
import capstone.letcomplete.group_group.dto.output.SavePostOutput;
import capstone.letcomplete.group_group.service.PostUsageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name="Post", description = "Post 관련 API")
public class PostController {
    private final PostUsageService postUsageService;

    @PostMapping
    @Operation(summary = "Creat Post", description = "모집글을 추가하는 API")
    public SavePostOutput savePost(@Valid @RequestBody CreatePostInput createPostInput) throws JsonProcessingException {
        Long savedPostId = postUsageService.savePost(createPostInput);
        return new SavePostOutput(savedPostId);
    }

    @GetMapping
    @Operation(summary = "Get Post Detail", description = "모집글에 대한 세부정보를 조회하는 API")
    public GetPostDetailOutput getPostDetail(
            @Schema(description = "조회할 모집글 ID")
            @RequestParam(name = "postId") Long id
    ) throws JsonProcessingException {
        return postUsageService.getPostDetail(id);
    }
}
