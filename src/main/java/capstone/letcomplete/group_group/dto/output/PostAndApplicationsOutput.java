package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.PostAndApplicationsOverviewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostAndApplicationsOutput {
    private Long postId;
    private String postTitle;
    private LocalDateTime postCreateTime;
    List<ApplicationOverViewOutput> applicationOverViewList;

    public PostAndApplicationsOutput(PostAndApplicationsOverviewDto dto) {
        this.postId = dto.getPostId();
        this.postTitle = dto.getPostTitle();
        this.postCreateTime = dto.getPostCreateTime();
        this.applicationOverViewList = dto.getApplicationOverViewList().stream().map(overview -> new ApplicationOverViewOutput(overview)).collect(Collectors.toList());
    }

}
