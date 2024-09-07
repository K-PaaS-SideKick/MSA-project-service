package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDTO {
    private Long cid;
    private String uid;
    private String content;
    private LocalDateTime created_at;
    private List<SubCommentDTO> subComments;
}

