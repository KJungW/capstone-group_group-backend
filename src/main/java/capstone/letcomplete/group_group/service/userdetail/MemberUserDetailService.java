package capstone.letcomplete.group_group.service.userdetail;

import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.exception.DataNotFoundException;
import capstone.letcomplete.group_group.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // DB에서 유저정보를 가져옴
            Member member = memberService.findById(Long.valueOf(username));
            // 유저정보를 바탕으로 UserDetail 객체를 만들어 리턴
            return new MemberUserDetail(member.getId(), member.getPassword(), member.getRole());
        } catch(DataNotFoundException e) {
            throw new UsernameNotFoundException( "id에 해당하는 회원이 존재하지 않습니다.");
        }
    }
}
