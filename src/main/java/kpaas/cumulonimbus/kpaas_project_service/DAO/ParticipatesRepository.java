package kpaas.cumulonimbus.kpaas_project_service.DAO;

import kpaas.cumulonimbus.kpaas_project_service.Entity.Participates;
import kpaas.cumulonimbus.kpaas_project_service.Entity.ParticipatesPK;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipatesRepository extends JpaRepository<Participates, ParticipatesPK> {
    List<Participates> findAllByPid(Project project);
    Boolean existsByPidAndUid(Project project, String uid);
    Optional<Participates> findByPidAndUid(Project project, String uid);
}
