package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubCommentDTO {
    private Long cid;
    private String uid;
    private String content;
    private LocalDateTime created_at;
}
