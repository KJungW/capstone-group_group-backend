package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.dto.input.SaveApplicationInput;
import capstone.letcomplete.group_group.entity.Application;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.Post;
import capstone.letcomplete.group_group.entity.RequirementsFormResult;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final RequirementsFormResultService requirementsFormResultService;

    @Transactional
    public Long saveApplication(SaveApplicationInput input, List<MultipartFile> inputFiles) throws IOException {
        // Application의 구성요소 세팅
        Post post = postService.findById(input.getPostId());
        Member member = memberService.findById(input.getApplicantId());
        Long formResultId = requirementsFormResultService.save(input, inputFiles);
        RequirementsFormResult formResult = requirementsFormResultService.findById(formResultId);

        // Application 저장
        Application newApplication = Application.makeApplication(post, member, false, formResult);
        applicationRepository.save(newApplication);

        return newApplication.getId();
    }

    public Application findById(Long id) {
        return applicationRepository.findById(id).orElseThrow(
                ()->new DataNotFoundException("id에 해당하는 Application이 존재하지 않습니다.")
        );
    }
}
