package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.dto.logic.SaveRequirementResultInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SaveApplicationInput {
    @NotNull
    @Schema(description = "신청목표인 모집글 ID")
    private Long postId;
    @NotNull
    @Schema(description = "신청자 ID")
    private Long applicantId;
    @NotNull
    @Schema(description = "참여요건 리스트")
    private List<SaveRequirementResultInput> requirementResultList = new ArrayList<>();
}
