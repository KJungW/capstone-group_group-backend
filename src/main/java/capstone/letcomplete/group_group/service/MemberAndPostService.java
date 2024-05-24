package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.Application;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.Post;
import capstone.letcomplete.group_group.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAndPostService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PostUsageService postUsageService;
    private final PostService postService;
    private final ApplicationService applicationService;

    @Transactional
    public void deleteMember(Long memberId) throws JsonProcessingException {
        Member member = memberService.findById(memberId);
        List<Post> postsInMember = postService.findAllByMember(memberId);
        List<Application> applicationInMember = applicationService.findAllApplicationsByMember(memberId);

        // 멤버와 관련된 모집글 지우기
        for(Post post : postsInMember) {
            postUsageService.deletePost(post.getId(), memberId);
        }

        //  멤버와 관련된 신청 지우기
        applicationService.deleteAll(applicationInMember.stream().map(Application::getId).collect(Collectors.toList()));
        
        // 멤버제거하기
        member.disableEmail();
        memberRepository.flush();
        memberRepository.delete(member);
    }
}
