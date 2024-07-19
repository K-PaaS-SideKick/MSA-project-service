package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Entity
public class Project {
    @Id
    private Long pid;

    private Long uid;

    private String title;

    private LocalDateTime date;

    @Column(nullable = false)
    private String content;

    private int upvotes;

    private int comments;

    private int views;

    private  int scraps;

    @Column(length = 1, nullable = false)
    private String status;

    private int max_members;

    private int current_members;
}
