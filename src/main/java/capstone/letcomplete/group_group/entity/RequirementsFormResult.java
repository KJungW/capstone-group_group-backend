package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequirementsFormResult extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENTS_FORM_ID", nullable = false)
    private RequirementsForm targetForm;

    @Column(nullable = false, columnDefinition = "JSON")
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
