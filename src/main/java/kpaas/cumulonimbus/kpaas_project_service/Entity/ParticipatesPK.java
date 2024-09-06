package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class ParticipatesPK implements Serializable{
    @ManyToOne
    @JoinColumn(name = "pid", nullable = false)
    private Project pid;

    private String uid;
}
