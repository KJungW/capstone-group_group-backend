package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResultDto {
    private String originFileName;
    private String serverFileName;
    private String uploadUrl;
}
