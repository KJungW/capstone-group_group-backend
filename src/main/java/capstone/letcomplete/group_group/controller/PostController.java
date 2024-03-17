package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.dto.output.GetPostDetailOutput;
import capstone.letcomplete.group_group.dto.output.SavePostOutput;
import capstone.letcomplete.group_group.service.PostUsageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostUsageService postUsageService;

    @PostMapping
    public SavePostOutput savePost(@Valid @RequestBody CreatePostInput createPostInput) throws JsonProcessingException {
        Long savedPostId = postUsageService.savePost(createPostInput);
        return new SavePostOutput(savedPostId);
    }

    @GetMapping
    public GetPostDetailOutput getPostDetail(@RequestParam(name = "postId") Long id) throws JsonProcessingException {
        return postUsageService.getPostDetail(id);
    }
}
