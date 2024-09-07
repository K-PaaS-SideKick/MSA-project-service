package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentDTO {
    private Long pid;
    private List<CommentDTO> comments;
}
