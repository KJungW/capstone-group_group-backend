package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.dto.input.UpdatePostInput;
import capstone.letcomplete.group_group.dto.output.GetPostDetailOutput;
import capstone.letcomplete.group_group.dto.output.SavePostOutput;
import capstone.letcomplete.group_group.service.PostUsageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name="Post", description = "Post 관련 API")
public class PostController {
    private final PostUsageService postUsageService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
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

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Delete Post", description = "모집글 제거")
    public void deletePost(
            @RequestParam(name="postId") Long postId
    ) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        postUsageService.deletePost(postId, memberId);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ME_COMMON')")
    @Operation(summary = "Update Post", description = "모집글 수정")
    public void updatePost (
            @Valid @RequestBody UpdatePostInput updatePostInput
    ) throws JsonProcessingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = Long.valueOf(userDetails.getUsername());
        postUsageService.updatePost(updatePostInput, memberId);
    }

}
