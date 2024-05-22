package capstone.letcomplete.group_group.repository.custom;

import capstone.letcomplete.group_group.dto.logic.PostOverViewDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    private final EntityManager em;

    @Override
    public Page<PostOverViewDto> findPageWithBoardAndTitle(int pageNum, int pageSize, Long boardId, List<String> searchWords) {
        StringBuilder searchConditionBuilder = new StringBuilder();
        for(String word : searchWords) {
            searchConditionBuilder.append(" and p.title like '%").append(word).append("%'");
        }
        String searchCondition = searchConditionBuilder.toString();

        String searchJpql = "select new capstone.letcomplete.group_group.dto.logic.PostOverViewDto(p.id, p.title, p.writer.nickName, p.createDate)" +
                " from Post p" +
                " where p.board.id = :boardId" + searchCondition +
                " order by p.createDate desc";
        String countJpql = "SELECT count (p)" +
                " from Post p" +
                " where p.board.id = :boardId" + searchCondition;

        List<PostOverViewDto> resultList = em.createQuery(searchJpql, PostOverViewDto.class)
                .setParameter("boardId", boardId)
                .setFirstResult(pageNum*pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        Long count = em.createQuery(countJpql, Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult();

        return new PageImpl<>(resultList, PageRequest.of(pageNum, pageSize), count);
    }
}
