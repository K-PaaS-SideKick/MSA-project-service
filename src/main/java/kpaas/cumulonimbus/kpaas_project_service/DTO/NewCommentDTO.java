package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.*;

@Getter
@Setter
@ToString
public class NewCommentDTO {
    private Long pid;
    private String uid;
    private String comment;
    private Long parent_comment;
}
