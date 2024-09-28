package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Project_CategoryPK.class)
@Builder
public class Project_Category {
    @Id
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @Id
    private int category;
}
