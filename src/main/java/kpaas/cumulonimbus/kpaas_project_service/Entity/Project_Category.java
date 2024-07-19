package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Project_Category {
    @EmbeddedId
    Project_CategoryPK projectCategoryPK;
}
