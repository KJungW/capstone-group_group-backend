package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    Optional<Campus> findByName(String name);

}
