package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.logic.DisabledAppPreviewsInPageDto;
import capstone.letcomplete.group_group.entity.DisabledApplication;
import capstone.letcomplete.group_group.repository.DisabledApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public DisabledAppPreviewsInPageDto findPreviewsInPageByMember(Long memberId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<DisabledApplication> previewsInPage = disabledApplicationRepository.findAllInPageByMember(memberId, pageRequest);
        return new DisabledAppPreviewsInPageDto(previewsInPage);
    }
}
