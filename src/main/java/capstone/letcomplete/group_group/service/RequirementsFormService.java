package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.RequirementsFormRepository;
import capstone.letcomplete.group_group.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequirementsFormService {
    private final RequirementsFormRepository requirementsFormRepository;
    private final JsonUtil jsonUtil;

    @Transactional
    public Long SaveRequirementsForm(RequirementsForm newForm) {
        RequirementsForm savedForm = requirementsFormRepository.save(newForm);
        return savedForm.getId();
    }

    public RequirementsForm findById(Long id) {
        return requirementsFormRepository.findById(id).orElseThrow(
                ()-> new DataNotFoundException("ID에 해당하는 Post가 존재하지 않습니다.")
        );
    }

}
