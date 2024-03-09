package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.Campus;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.CampusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampusService {

    private final CampusRepository campusRepository;

    public Campus findById(Long id) {
        return campusRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("해당 id의 캠버스가 존재하지 않습니다."));
    }
}
