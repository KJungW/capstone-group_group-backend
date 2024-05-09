package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.dto.logic.ApplicationAndResultDto;
import capstone.letcomplete.group_group.dto.logic.ApplicationOverviewDto;
import capstone.letcomplete.group_group.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @EntityGraph(attributePaths = {"post", "applicant", "requirementsFormResult"})
    @Query("select a from Application a where a.id = :id")
    Optional<Application> findFullApplicationById(@Param("id") Long id);

    @Query("select new capstone.letcomplete.group_group.dto.logic.ApplicationOverviewDto(a.post.id, a.id, a.applicant.nickName, a.isPassed) from Application a where a.post.id in :postIdList")
    List<ApplicationOverviewDto> findApplicationsInPosts(@Param("postIdList") List<Long> postIdList);

    @Query("select new capstone.letcomplete.group_group.dto.logic.ApplicationAndResultDto(a.id, a.isPassed, a.post.id, a.post.title, a.post.openChatUrl, a.createDate) from Application a where a.applicant.id = :memberId")
    Page<ApplicationAndResultDto> findApplicationsInMember(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select a from Application a where a.post.id = :postId")
    List<Application> findAllByPost(@Param("postId") Long postId);
}
