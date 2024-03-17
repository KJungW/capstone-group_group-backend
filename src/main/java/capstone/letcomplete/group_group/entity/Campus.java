package capstone.letcomplete.group_group.entity;


import capstone.letcomplete.group_group.entity.auditing.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Campus extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    public static Campus makeCampus(String name) {
        Campus campus = new Campus();
        campus.name = name;
        return campus;
    }
}
