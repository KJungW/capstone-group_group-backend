package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.DisabledApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DisabledApplicationRepository  extends JpaRepository<DisabledApplication, Long> {
    @Query("select a from DisabledApplication a where a.applicantId = :memberId")
    Page<DisabledApplication> findAllInPageByMember(@Param("memberId") Long memberId, PageRequest pageRequest);
}