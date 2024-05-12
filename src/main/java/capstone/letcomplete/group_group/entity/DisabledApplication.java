package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisabledApplication {
    @Id
    @GeneratedValue
    private Long id;
    private Long applicationId;
    private Long applicantId;
    private Long postId;
    private String postTitle;
    private ApplicationState isPassed;
    private LocalDateTime createTime;

    public static DisabledApplication createDisabledApplication(
            Long applicationId, Long applicantId, Long postId, String postTitle, ApplicationState isPassed, LocalDateTime createTime
    ) {
        DisabledApplication entity = new DisabledApplication();
        entity.applicationId  = applicationId;
        entity.applicantId = applicantId;
        entity.postId = postId;
        entity.postTitle = postTitle;
        entity.isPassed = isPassed;
        entity.createTime = createTime;
        return entity;
    }
}
