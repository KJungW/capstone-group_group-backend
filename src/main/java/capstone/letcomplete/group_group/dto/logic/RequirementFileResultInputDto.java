package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class RequirementFileResultInputDto {
    private String requirementId;
    private MultipartFile fileResult;
}
