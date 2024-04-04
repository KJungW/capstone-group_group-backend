package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostOverViewDto {
    private Long id;
    private String title;
    private LocalDateTime createDate;
}
