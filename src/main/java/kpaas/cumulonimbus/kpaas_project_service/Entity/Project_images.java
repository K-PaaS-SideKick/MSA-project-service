package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;

@Entity
public class Project_images {
    @Id
    private Long image_id;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Project pid;

    @Column(nullable = false)
    private String path;
}
