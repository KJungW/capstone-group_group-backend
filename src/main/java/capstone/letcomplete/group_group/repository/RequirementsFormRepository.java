package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.RequirementsForm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequirementsFormRepository extends JpaRepository<RequirementsForm, Long> {
    @Query( "select f from RequirementsForm f where f.post.id = :postId")
    List<RequirementsForm> findByPostId(@Param("postId") Long id, Pageable pageable);


}

