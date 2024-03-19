package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.Application;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application findById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 Application이 존재하지 않습니다.")
        );
    }
}
