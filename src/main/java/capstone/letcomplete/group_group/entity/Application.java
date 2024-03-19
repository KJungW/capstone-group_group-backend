package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID", nullable = false)
    private Member applicant;

    @Column(nullable = false)
    private boolean isPassed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_FORM_RESULT_ID", nullable = false)
    private RequirementsFormResult requirementsFormResult;

    public static Application makeApplication(
            Post post, Member applicant, Boolean isPassed,
            RequirementsFormResult requirementsFormResult
    ) {
        Application appilcation = new Application();
        appilcation.post = post;
        appilcation.applicant = applicant;
        appilcation.isPassed = isPassed;
        appilcation.requirementsFormResult = requirementsFormResult;
        return appilcation;
    }
}
