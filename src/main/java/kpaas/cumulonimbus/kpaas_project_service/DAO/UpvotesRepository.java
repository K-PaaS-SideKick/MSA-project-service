package kpaas.cumulonimbus.kpaas_project_service.DAO;

import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Upvotes;
import kpaas.cumulonimbus.kpaas_project_service.Entity.UpvotesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpvotesRepository extends JpaRepository<Upvotes, UpvotesPK> {
    void deleteByPidAndUid(Project project, String uid);
    boolean existsByPidAndUid(Project project, String uid);
}
