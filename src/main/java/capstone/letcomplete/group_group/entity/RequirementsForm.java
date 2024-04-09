package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequirementsForm extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID", nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "JSON")
    private String requirements;
    
    public static RequirementsForm makeRequirementsForm(
            Post post, String requirements
    ) {
        RequirementsForm result = new RequirementsForm();
        result.post = post;
        result.requirements = requirements;
        return result;
    }
}
