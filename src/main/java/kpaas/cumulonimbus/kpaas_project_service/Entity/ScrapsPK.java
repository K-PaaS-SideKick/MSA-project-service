package kpaas.cumulonimbus.kpaas_project_service.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ScrapsPK implements Serializable {
    private Long project;

    private String uid;
}
