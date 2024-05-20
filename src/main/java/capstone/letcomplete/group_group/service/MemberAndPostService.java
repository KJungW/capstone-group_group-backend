package capstone.letcomplete.group_group.service;

import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.Post;
import capstone.letcomplete.group_group.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAndPostService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PostUsageService postUsageService;
    private final PostService postService;

    @Transactional
    public void deleteMember(Long memberId) throws JsonProcessingException {
        Member member = memberService.findById(memberId);
        List<Post> postsInMember = postService.findAllByMember(memberId);

        for(Post post : postsInMember) {
            postUsageService.deletePost(post.getId(), memberId);
        }
        memberRepository.delete(member);
    }
}
