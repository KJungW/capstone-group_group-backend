package capstone.letcomplete.group_group.repository.custom;

import capstone.letcomplete.group_group.dto.logic.PostOverViewDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostRepositoryCustom {
    Page<PostOverViewDto> findPageWithBoardAndTitle(int pageNum, int pageSize, Long boardId, List<String> searchWords);
}
