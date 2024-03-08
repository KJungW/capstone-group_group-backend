package capstone.letcomplete.group_group.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequirementsForm {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="POST_ID", nullable = false)
    private Post post;

    @Column(nullable = false)
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
