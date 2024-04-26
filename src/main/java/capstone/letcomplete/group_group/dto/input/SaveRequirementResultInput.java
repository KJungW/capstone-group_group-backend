package capstone.letcomplete.group_group.dto.input;

import capstone.letcomplete.group_group.entity.enumtype.RequirementResultType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveRequirementResultInput {
    @NotNull
    @Schema(description = "참여요건 ID")
    private String requirementId;
    @NotNull
    @Schema(description = "참여요건 제출물 타입")
    private RequirementResultType type;
    @NotNull
    @Schema(description = "제출물 내용(텍스트타입이면 실제 내용이, 파일이면 파일명이 들어가는 곳")
    private String content;
}
