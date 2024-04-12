package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.Campus;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.exception.InvalidInputException;
import capstone.letcomplete.group_group.repository.CampusRepository;
import capstone.letcomplete.group_group.value.DefaultData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampusService {

    private final CampusRepository campusRepository;

    @Transactional
    public Long save(String campusName) {
        validateCampusName(campusName);
        Campus campus = campusRepository.save(Campus.makeCampus(campusName));
        return campus.getId();
    }

    public void validateCampusName(String name) {
        if(campusRepository.findByName(name).isPresent())
            throw new InvalidInputException("캠버스 이름이 중복입니다.");
    }

    public Campus findById(Long id) {
        return campusRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("해당 id의 캠버스가 존재하지 않습니다."));
    }

    public void checkCampusExistence(Long id) {
        findById(id);
    }

    public Campus findDefaultCampus() {
        return campusRepository.findByName(DefaultData.defaultCampusName).orElseThrow(
                () -> new InvalidInputException("디폴트 캠퍼스에 해당하는 데이터가 없습니다."));
    }
}
