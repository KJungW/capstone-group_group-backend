package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
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
@SQLDelete(sql = "UPDATE application SET deleted_At = NOW() WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
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
    @JoinColumn(name="REQUIREMENT_FORM_RESULT_ID")
    private RequirementsFormResult requirementsFormResult;

    private LocalDateTime deletedAt;

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

    public void changeApplicationState(ApplicationState state) {
        this.isPassed = state;
    }

    public void disconnectRequirementsFormResult() {
        this.requirementsFormResult = null;
    }

}
