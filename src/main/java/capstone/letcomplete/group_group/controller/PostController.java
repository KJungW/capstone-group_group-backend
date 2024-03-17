package capstone.letcomplete.group_group.controller;

import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.service.PostUsageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostUsageService postUsageService;

    @PostMapping
    public void savePost(@Valid @RequestBody CreatePostInput createPostInput) throws JsonProcessingException {
        postUsageService.savePost(createPostInput);
    }
}
