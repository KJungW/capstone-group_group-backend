package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickname);
}
