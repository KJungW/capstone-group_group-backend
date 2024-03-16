package capstone.letcomplete.group_group;

import capstone.letcomplete.group_group.dto.input.SignupMangerInput;
import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.entity.enumtype.ManagerRoleType;
import capstone.letcomplete.group_group.repository.ManagerRepository;
import capstone.letcomplete.group_group.service.CampusService;
import capstone.letcomplete.group_group.service.ManagerService;
import capstone.letcomplete.group_group.service.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class DataSetting {
    private final ManagerRepository managerRepository;
    private final ManagerService managerService;
    private final MemberService memberService;
    private final CampusService campusService;
    private final Environment env;
    @PostConstruct
    private void init() {
        if(checkDBIsEmpty()) {
            createManager();
            /*
             운영환경일 경우 제거
             */
            createExampleData();
        }
    }

    private boolean checkDBIsEmpty() {
        return managerRepository.findByEmail("manager0@back.com").isEmpty();
    }

    private void createManager() {
        SignupMangerInput manager0 = new SignupMangerInput(
                env.getProperty("initialData.manager.manager0.email"),
                env.getProperty("initialData.manager.manager0.pw"),
                "manager0", ManagerRoleType.MG_COMMON);

        SignupMangerInput manager1 = new SignupMangerInput(
                env.getProperty("initialData.manager.manager1.email"),
                env.getProperty("initialData.manager.manager1.pw"),
                "manager1", ManagerRoleType.MG_COMMON);

        SignupMangerInput manager2 = new SignupMangerInput(
                env.getProperty("initialData.manager.manager2.email"),
                env.getProperty("initialData.manager.manager2.pw"),
                "manager2", ManagerRoleType.MG_COMMON);

        SignupMangerInput manager3 = new SignupMangerInput(
                env.getProperty("initialData.manager.manager3.email"),
                env.getProperty("initialData.manager.manager3.pw"),
                "manager3", ManagerRoleType.MG_COMMON);

        managerService.signup(manager0);
        managerService.signup(manager1);
        managerService.signup(manager2);
        managerService.signup(manager3);
    }

    private void createExampleData() {
        Long campusId = campusService.save("Hansung");
        memberService.saveMember(new SignupMemberInput("test@test.com", "test1234", "Test", campusId));

    }
}
