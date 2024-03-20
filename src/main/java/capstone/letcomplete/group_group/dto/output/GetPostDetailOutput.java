package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.entity.enumtype.PassionSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostDetailOutput {
    @Schema(description = "모집글이 속한 게시판 ID")
    private Long boardId;
    @Schema(description = "모집글이 속한 게시판 이름")
    private String boardName;
    
    @Schema(description = "모집글 작성자 ID")
    private Long writerId;
    @Schema(description = "모집글 작성한 닉네임")
    private String writerNickname;

    @Schema(description = "모집글 제목")
    private String title;
    @Schema(description = "활동내용")
    private String activityDetail;
    @Schema(description = "열정 정도")
    private PassionSize passionSize;
    @Schema(description = "더하고싶은 말")
    private String additionalWriting;

    @Schema(description = "참여요건 리스트")
    private List<GetRequirementOutput> requirementList;
}
