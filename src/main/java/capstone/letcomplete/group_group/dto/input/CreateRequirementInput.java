package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRequirementInput {
    @NotBlank
    @Schema(description = "참여요건 제목")
    private String title;
    @NotNull
    @Schema(description = "참여요건 제출물 타입")
    private RequirementResultType resultType;
}
