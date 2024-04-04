package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.logic.PostOverViewDto;
import capstone.letcomplete.group_group.dto.output.GetPostsInBoardOutput;
import capstone.letcomplete.group_group.entity.Post;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long savePost(Post post) {
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("ID에 해당하는 Post가 존재하지 않습니다.")
        );
    }

    public GetPostsInBoardOutput findPostOverViewInBoard(int pageNumber, int pageSize, Long boardId) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<PostOverViewDto> result = postRepository.findPostsInBoard(boardId, pageRequest);
        return new GetPostsInBoardOutput(result.getContent(), result.getTotalPages(), result.getNumber(), result.isLast(), result.isFirst());
    }

}
