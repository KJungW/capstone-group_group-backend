package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import capstone.letcomplete.group_group.entity.enumtype.MemberRoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_At = NOW() WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleType role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPUS_ID", nullable = false)
    private Campus campus;

    private LocalDateTime deletedAt;

    public static Member makeMember(String email, String password, String nickName, MemberRoleType role, Campus campus) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickName = nickName;
        member.role = role;
        member.campus = campus;
        return member;
    }

    public void disableEmail() {
        this.email = this.email + "@@@@" +this.id;
    }
}