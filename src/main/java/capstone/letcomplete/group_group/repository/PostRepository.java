package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
