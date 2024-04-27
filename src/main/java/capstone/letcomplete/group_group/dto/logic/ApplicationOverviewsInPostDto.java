package capstone.letcomplete.group_group.dto.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApplicationOverviewsInPostDto {
    private Long postId;
    private List<ApplicationOverviewDto> applicationOverviewDtoList = new ArrayList<>();
}
