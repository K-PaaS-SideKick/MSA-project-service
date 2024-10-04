package kpaas.cumulonimbus.kpaas_project_service.DAO;

import kpaas.cumulonimbus.kpaas_project_service.DTO.ProjectSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectCustomRepository {
    Page<ProjectSummary> findAllWithTitle(String title, Pageable pageable);
    Page<ProjectSummary> findAllWithPageable(Pageable pageable);
}
