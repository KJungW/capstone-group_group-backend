package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.dto.logic.PostOverViewDto;
import capstone.letcomplete.group_group.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.writer.id = :writerId")
    List<Post> findAllInMember(@Param("writerId") Long memberId);

    @Query("select new capstone.letcomplete.group_group.dto.logic.PostOverViewDto(p.id, p.title, p.writer.nickName, p.createDate) from Post p where p.board.id = :boardId")
    Page<PostOverViewDto> findPostsInBoard(@Param("boardId") Long boardId, Pageable pageable);

    @Query("select new capstone.letcomplete.group_group.dto.logic.PostOverViewDto(p.id, p.title, p.writer.nickName, p.createDate) from Post p where p.writer.id = :memberId")
    Page<PostOverViewDto> findPostsInMember(@Param("memberId") Long memberId, Pageable pageable);
}
