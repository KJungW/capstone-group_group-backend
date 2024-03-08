package capstone.letcomplete.group_group.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appilcation {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="POST_ID", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID", nullable = false)
    private Member applicant;

    @Column(nullable = false)
    private boolean isPassed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_FORM_RESULT_ID", nullable = false)
    private RequirementsFormResult requirementsFormResult;

    public static Appilcation makeApplication(
            Post post, Member applicant, Boolean isPassed,
            RequirementsFormResult requirementsFormResult
    ) {
        Appilcation appilcation = new Appilcation();
        appilcation.post = post;
        appilcation.applicant = applicant;
        appilcation.isPassed = isPassed;
        appilcation.requirementsFormResult = requirementsFormResult;
        return appilcation;
    }
}
