package capstone.letcomplete.group_group.dto.logic;

import capstone.letcomplete.group_group.entity.DisabledApplication;
import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DisabledAppPreviewDto {
    private Long id;
    private String postTitle;
    private ApplicationState isPassed;
    private LocalDateTime createTime;

    public DisabledAppPreviewDto(DisabledApplication dto) {
        this.id = dto.getId();
        this.postTitle = dto.getPostTitle();
        this.isPassed = dto.getIsPassed();
        this.createTime = dto.getApplicationCreatedDate();
    }
}
