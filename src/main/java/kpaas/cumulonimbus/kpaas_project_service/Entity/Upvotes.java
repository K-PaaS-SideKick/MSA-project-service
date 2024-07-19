package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Upvotes {
    @EmbeddedId
    private UpvotesPK upvotesPK;
}
