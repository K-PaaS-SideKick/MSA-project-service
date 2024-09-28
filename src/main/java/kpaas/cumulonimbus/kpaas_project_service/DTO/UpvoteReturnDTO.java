package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpvoteReturnDTO {
    private Long pid;
    private int upvotes;
}
