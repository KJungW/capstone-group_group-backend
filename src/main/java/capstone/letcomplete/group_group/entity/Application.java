package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
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
    @Enumerated(value = EnumType.STRING)
    private ApplicationState isPassed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_FORM_RESULT_ID", nullable = false)
    private RequirementsFormResult requirementsFormResult;

    public static Application makeApplication(
            Post post, Member applicant, ApplicationState isPassed,
            RequirementsFormResult requirementsFormResult
    ) {
        Application application = new Application();
        application.post = post;
        application.applicant = applicant;
        application.isPassed = isPassed;
        application.requirementsFormResult = requirementsFormResult;
        return application;
    }
}
