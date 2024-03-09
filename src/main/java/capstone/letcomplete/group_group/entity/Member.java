package capstone.letcomplete.group_group.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @ManyToOne
    @JoinColumn(name="CAMPUS_ID", nullable = false)
    private Campus campus;

    public static Member makeMember(String email, String password, String nickName, Campus campus) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.campus = campus;
        return member;
    }
}
