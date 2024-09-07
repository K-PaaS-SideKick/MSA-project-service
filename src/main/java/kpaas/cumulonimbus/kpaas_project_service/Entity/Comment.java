package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long cid;

    private String uid;

    private String content;

    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "parent_comment")
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "parent_pid", nullable = false)
    private Project parentPid;
}
