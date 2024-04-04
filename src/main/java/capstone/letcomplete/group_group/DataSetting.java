package capstone.letcomplete.group_group;

import capstone.letcomplete.group_group.dto.input.CreatePostInput;
import capstone.letcomplete.group_group.dto.input.CreateRequirementInput;
import capstone.letcomplete.group_group.dto.input.SignupMangerInput;
import capstone.letcomplete.group_group.dto.input.SignupMemberInput;
import capstone.letcomplete.group_group.entity.Member;
import capstone.letcomplete.group_group.entity.enumtype.ManagerRoleType;
import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import capstone.letcomplete.group_group.repository.ManagerRepository;
import capstone.letcomplete.group_group.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.yaml")
public class DataSetting {
    private final ManagerRepository managerRepository;
    private final ManagerService managerService;
    private final MemberService memberService;
    private final CampusService campusService;
    private final BoardService boardService;
    private final PostUsageService postUsageService;
    private final Environment env;
    @PostConstruct
    private void init() throws JsonProcessingException {
        if(checkDBIsEmpty()) {
            createInitData();
        }
    }

    private boolean checkDBIsEmpty() {
        return managerRepository.findByEmail("manager0@back.com").isEmpty();
    }

    private void createInitData() throws JsonProcessingException {
        // 초기 관리자 계정 추가
        Long manager0Id = createInitManager(
                env.getProperty("initialData.manager.manager0.email"),
                env.getProperty("initialData.manager.manager0.pw"),
                "manager0", ManagerRoleType.MG_COMMON
        );
        Long manager1Id = createInitManager(
                env.getProperty("initialData.manager.manager1.email"),
                env.getProperty("initialData.manager.manager1.pw"),
                "manager1", ManagerRoleType.MG_COMMON
        );
        Long manager2Id = createInitManager(
                env.getProperty("initialData.manager.manager2.email"),
                env.getProperty("initialData.manager.manager2.pw"),
                "manager2", ManagerRoleType.MG_COMMON
        );
        Long manager3Id = createInitManager(
                env.getProperty("initialData.manager.manager3.email"),
                env.getProperty("initialData.manager.manager3.pw"),
                "manager3", ManagerRoleType.MG_COMMON
        );

        // 초기 대학교 추가
        Long campusId = createInitCampus("한성대학교");
        
        // 초기 일반회원 계정추가
        Long member0 = createInitMember(
                env.getProperty("initialData.member.member0.email"),
                env.getProperty("initialData.member.member0.pw"),
                "member0", campusId
        );
        Long member1 = createInitMember(
                env.getProperty("initialData.member.member1.email"),
                env.getProperty("initialData.member.member1.pw"),
                "member1", campusId
        );
        Long member2 = createInitMember(
                env.getProperty("initialData.member.member2.email"),
                env.getProperty("initialData.member.member2.pw"),
                "member2", campusId
        );
        Long member3 = createInitMember(
                env.getProperty("initialData.member.member3.email"),
                env.getProperty("initialData.member.member3.pw"),
                "member3", campusId
        );

        // 게시판 추가
        Long boardId = boardService.createBoard(campusId, "조별과제/캡스톤$IT공과");
        boardService.createBoard(campusId, "조별과제/캡스톤$인문예술");
        boardService.createBoard(campusId, "조별과제/캡스톤$사회과학");
        boardService.createBoard(campusId, "조별과제/캡스톤$디자인");
        boardService.createBoard(campusId, "대회/공모전$IT공과");
        boardService.createBoard(campusId, "대회/공모전$인문예술");
        boardService.createBoard(campusId, "대회/공모전$사회과학");
        boardService.createBoard(campusId, "대회/공모전$디자인");
        boardService.createBoard(campusId, "스터디$IT공과");
        boardService.createBoard(campusId, "스터디$인문예술");
        boardService.createBoard(campusId, "스터디$사회과학");
        boardService.createBoard(campusId, "스터디$디자인");

        // 모집글추가
        for(int i=0;i<30;i++) {
            Long postId = createPost(
                    boardId, member0, "모집글"+i, "디테일", PassionSize.MIDDLE, "추가", "URL",
                    new CreateRequirementInput("요건1", RequirementResultType.TEXT),
                    new CreateRequirementInput("요건2", RequirementResultType.FILE),
                    new CreateRequirementInput("요건3", RequirementResultType.TEXT)
            );
        }

    }

    private Long createInitManager(String email, String pw, String nickName, ManagerRoleType role) {
        return managerService.signup(new SignupMangerInput(email, pw,  nickName, role));
    }

    private Long createInitMember(String email, String pw, String nickName, Long campusId) {
        Member savedMember = memberService.saveMember(new SignupMemberInput(email, pw, nickName, campusId));
        return savedMember.getId();
    }

    private Long createInitCampus(String campusName) {
        return campusService.save(campusName);
    }

    private Long createBoard(Long campusId, String boardTitle) {
        return boardService.createBoard(campusId, boardTitle);
    }

    private Long createPost(
            Long boardID, Long writerId, String title, String activityDetail,
            PassionSize passionSize, String additionalWriting, String openChatUrl,
            CreateRequirementInput requirement1, CreateRequirementInput requirement2, CreateRequirementInput requirement3
    ) throws JsonProcessingException {
        List<CreateRequirementInput> requirementInputList = new ArrayList<>();
        requirementInputList.add(requirement1);
        requirementInputList.add(requirement2);
        requirementInputList.add(requirement3);

        return postUsageService.savePost(new CreatePostInput(
                boardID, writerId, title, activityDetail, passionSize, additionalWriting,
                openChatUrl, requirementInputList
        ));
    }

}
