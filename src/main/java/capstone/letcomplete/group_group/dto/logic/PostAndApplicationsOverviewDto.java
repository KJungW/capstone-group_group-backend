package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostAndApplicationsOverviewDto {
    private Long postId;
    private String postTitle;
    private LocalDateTime postCreateTime;
    private List<ApplicationOverviewDto> applicationOverViewList;

    public void changeApplicationOverViewList(List<ApplicationOverviewDto> list) {
        applicationOverViewList = list;
    }
}
