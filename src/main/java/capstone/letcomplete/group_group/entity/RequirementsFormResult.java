package capstone.letcomplete.group_group.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequirementsFormResult {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="REQUIREMENTS_FORM_ID", nullable = false)
    private RequirementsForm targetForm;

    @Column(nullable = false)
    private String requirementResults;

    public static RequirementsFormResult makeRequirementsFormResult(
            RequirementsForm targetForm, String requirementResults
    ) {
        RequirementsFormResult result = new RequirementsFormResult();
        result.targetForm = targetForm;
        result.requirementResults = requirementResults;
        return result;
    }
}
