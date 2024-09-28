package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@IdClass(ScrapsPK.class)
public class Scraps {
    @Id
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Project project;

    @Id
    private String uid;
}
