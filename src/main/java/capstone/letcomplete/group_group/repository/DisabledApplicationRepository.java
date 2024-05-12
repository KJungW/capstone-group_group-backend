package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.DisabledApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabledApplicationRepository  extends JpaRepository<DisabledApplication, Long> {
}