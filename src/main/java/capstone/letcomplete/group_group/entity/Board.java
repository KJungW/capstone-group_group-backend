package capstone.letcomplete.group_group.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CAMPUS_ID", nullable = false)
    private Campus campus;

    @Column (nullable = false)
    private String title;

    public static Board makeBoard(Campus campus, String title) {
        Board board = new Board();
        board.campus = campus;
        board.title = title;
        return board;
    }
}
