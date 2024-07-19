package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class ScrapsPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Project pid;

    private Long uid;
}
