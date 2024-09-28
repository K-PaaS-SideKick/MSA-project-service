package kpaas.cumulonimbus.kpaas_project_service.DAO;

import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Scraps;
import kpaas.cumulonimbus.kpaas_project_service.Entity.ScrapsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapsRepository extends JpaRepository<Scraps, ScrapsPK> {
    Optional<Scraps> findByProjectAndUid(Project project, String uid);
    List<Scraps> findAllByUid(String uid);
}
