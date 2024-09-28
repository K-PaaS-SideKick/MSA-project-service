package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ParticipatesPK.class)
public class Participates {
    @Id
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Project pid;

    @Id
    private String uid;

    private boolean active;
}
