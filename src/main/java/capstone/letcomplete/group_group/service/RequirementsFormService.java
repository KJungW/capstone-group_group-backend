package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.RequirementsForm;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.RequirementsFormRepository;
import capstone.letcomplete.group_group.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public RequirementsForm findNewestWithPostId(Long id) {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createDate"));
        List<RequirementsForm> forms = requirementsFormRepository.findByPostId(id, pageRequest);
        if(forms.isEmpty())
            throw new DataNotFoundException("id에 해당하는 RequirementsForm이 존재하지 않습니다.");
        return forms.get(0);
    }

}
