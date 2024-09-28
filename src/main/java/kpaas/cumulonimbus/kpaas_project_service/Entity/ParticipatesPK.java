package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipatesPK implements Serializable{
    private Project pid;

    private String uid;
}
