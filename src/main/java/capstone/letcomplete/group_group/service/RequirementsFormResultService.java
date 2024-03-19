package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.RequirementsFormResult;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.RequirementsFormResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequirementsFormResultService {
    private final RequirementsFormResultRepository formResultRepository;

    public RequirementsFormResult findById(Long id) {
        return formResultRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 RequirementsFormResult가 존재하지 않습니다.")
        );
    }
}
