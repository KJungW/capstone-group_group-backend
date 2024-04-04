package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.output.GetBoardListInCampusOutput;
import capstone.letcomplete.group_group.entity.Board;
import capstone.letcomplete.group_group.entity.Campus;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CampusService campusService;

    @Transactional
    public Long createBoard(Long campusId, String title) {
        Campus campus = campusService.findById(campusId);
        Board newBoard = Board.makeBoard(campus, title);
        boardRepository.save(newBoard);
        return newBoard.getId();
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("ID에 해당하는 게시판이 존재하지 않습니다.")
        );
    }

    public GetBoardListInCampusOutput findByCampus(Long campusId) {
        return new GetBoardListInCampusOutput(boardRepository.findByCampusId(campusId));
    }
}
