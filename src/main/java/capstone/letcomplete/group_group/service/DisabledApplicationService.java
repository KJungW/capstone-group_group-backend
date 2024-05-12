package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.DisabledApplication;
import capstone.letcomplete.group_group.repository.DisabledApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DisabledApplicationService {
    private final DisabledApplicationRepository disabledApplicationRepository;
    @Transactional
    public Long save(DisabledApplication disabledApplication) {
        DisabledApplication saved = disabledApplicationRepository.save(disabledApplication);
        return saved.getId();
    }
}
