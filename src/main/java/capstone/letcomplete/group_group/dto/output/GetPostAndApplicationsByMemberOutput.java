package capstone.letcomplete.group_group.dto.output;

import capstone.letcomplete.group_group.dto.logic.PostAndApplicationsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GetPostAndApplicationsByMemberOutput {
    List<PostAndApplicationsOutput> postAndApplicationsOverviews;
    private int sliceNum;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;

    public GetPostAndApplicationsByMemberOutput(PostAndApplicationsDto dto) {
        this.postAndApplicationsOverviews = dto.getPostAndApplicationsOverviews().stream().map(overview -> new PostAndApplicationsOutput(overview)).collect(Collectors.toList());
        this.sliceNum  = dto.getSliceNum();
        this.isFirst = dto.isFirst();
        this.isLast = dto.isLast();
        this.hasNext = dto.isHasNext();
    }

}
