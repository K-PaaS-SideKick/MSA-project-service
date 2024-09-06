package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Project_comment {
    @Id
    private Long comment_id;

    private String uid;

    private String content;

    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "parent_comment")
    private Project_comment parent_comment;

    @ManyToOne
    @JoinColumn(name = "parent_pid", nullable = false)
    private Project parent_pid;
}
