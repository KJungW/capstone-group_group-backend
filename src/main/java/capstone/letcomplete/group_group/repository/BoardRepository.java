package capstone.letcomplete.group_group.repository;

import capstone.letcomplete.group_group.dto.logic.BoardOverviewDto;
import capstone.letcomplete.group_group.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select new capstone.letcomplete.group_group.dto.logic.BoardOverviewDto(b.id, b.title) from Board b where b.campus.id = :campusId" )
    List<BoardOverviewDto> findByCampusId(@Param("campusId") Long campusId);
}
