package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.DisabledAppPreviewDto;
import capstone.letcomplete.group_group.entity.enumtype.ApplicationState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DisabledAppPreviewOutput {
    @Schema(description = "비활성화된 신청 ID")
    private Long id;
    @Schema(description = "전체 페이지수")
    private String postTitle;
    @Schema(description = "신청 수락/거부 여부")
    private ApplicationState isPassed;
    @Schema(description = "신청 생성시간")
    private LocalDateTime createTime;

    public DisabledAppPreviewOutput(DisabledAppPreviewDto dto) {
        this.id = dto.getId();
        this.postTitle = dto.getPostTitle();
        this.isPassed = dto.getIsPassed();
        this.createTime = dto.getCreateTime();
    }
}
