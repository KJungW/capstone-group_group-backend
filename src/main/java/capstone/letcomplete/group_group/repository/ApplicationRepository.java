package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
