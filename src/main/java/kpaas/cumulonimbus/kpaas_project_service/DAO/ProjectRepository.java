package kpaas.cumulonimbus.kpaas_project_service.DAO;

import jakarta.annotation.Nullable;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {
    Page<Project> findAllByOrderByDateDesc(Pageable pageable);
    Page<Project> findAllByOrderByDateAsc(Pageable pageable);
    Page<Project> findAllByOrderByViewsDesc(Pageable pageable);
    Page<Project> findAllByOrderByUpvotesDesc(Pageable pageable);
    List<Project> findAllByUid(String uid);
}
