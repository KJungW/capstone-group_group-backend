package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.entity.enumtype.ManagerRoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ManagerRoleType role;

    public static Manager makeManager(
            String email, String password, String nickName, ManagerRoleType role) {
        Manager manager = new Manager();
        manager.email = email;
        manager.password = password;
        manager.nickName = nickName;
        manager.role = role;
        return manager;
    }
}
