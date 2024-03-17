package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.RequirementsFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequirementsFormService {
    private final RequirementsFormRepository requirementsFormRepository;

    public RequirementsForm findById(Long id) {
        return requirementsFormRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("ID에 해당하는 Post가 존재하지 않습니다.")
        );
    }
}
