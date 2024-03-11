package capstone.letcomplete.group_group.entity;

import capstone.letcomplete.group_group.dto.entitymake.MakePostDto;
import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOARD_ID", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_ID", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String activityDetail;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PassionSize passionSize;

    @Column(nullable = false)
    private String additionalWriting;

    @Column(nullable = false)
    private String openChatUrl;

    public static Post makePost(MakePostDto dto) {
        Post post = new Post();
        post.board = dto.getBoard();
        post.writer = dto.getWriter();
        post.title = dto.getTitle();
        post.activityDetail = dto.getActivityDetail();
        post.passionSize = dto.getPassionSize();
        post.additionalWriting = dto.getAdditionalWriting();
        post.openChatUrl = dto.getOpenChatUrl();
        return post;
    }

}
