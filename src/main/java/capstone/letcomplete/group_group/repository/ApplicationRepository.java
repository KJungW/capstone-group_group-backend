package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.dto.logic.ApplicationOverviewDto;
import capstone.letcomplete.group_group.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("select new capstone.letcomplete.group_group.dto.logic.ApplicationOverviewDto(a.post.id, a.id, a.applicant.nickName, a.isPassed) from Application a where a.post.id in :postIdList")
    List<ApplicationOverviewDto> findApplicationsInPosts(@Param("postIdList") List<Long> postIdList);
}
