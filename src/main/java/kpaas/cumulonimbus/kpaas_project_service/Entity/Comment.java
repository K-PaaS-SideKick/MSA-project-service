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
    private Comment parent_comment;

    @ManyToOne
    @JoinColumn(name = "parent_pid", nullable = false)
    private Project parent_pid;
}
