package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue
    private Long pid;

    private String uid;

    private String title;

    private LocalDateTime date;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    private String repo_link;

    private int upvotes;

    private int comments;

    private int views;

    private  int scraps;

    @Column(length = 1, nullable = false)
    private String status;

    private int max_members;

    private int current_members;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Image> images;
}