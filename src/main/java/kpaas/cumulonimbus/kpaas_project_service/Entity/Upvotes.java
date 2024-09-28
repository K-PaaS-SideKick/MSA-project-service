package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UpvotesPK.class)
public class Upvotes {
    @Id
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Project pid;

    @Id
    private String uid;
}
