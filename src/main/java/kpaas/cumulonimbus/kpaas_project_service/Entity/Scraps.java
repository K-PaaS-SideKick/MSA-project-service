package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Scraps {
    @EmbeddedId
    private ScrapsPK scrapsPK;
}
