package kpaas.cumulonimbus.kpaas_project_service.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Project_CategoryPK implements Serializable {
    private Long project;

    private int category;

//    @Override
//    public boolean equals(Object obj) {
//        if(this == obj) return true;
//        if(obj == null || getClass() != obj.getClass()) return false;
//        Project_CategoryPK that = (Project_CategoryPK) obj;
//        return Objects.equals(pid, that.pid) && Objects.equals(category, that.category);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(pid, category);
//    }
}
