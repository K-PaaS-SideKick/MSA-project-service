package kpaas.cumulonimbus.kpaas_project_service.DAO;

import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project_Category;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project_CategoryPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProjectCategoryRepository extends JpaRepository<Project_Category, Project_CategoryPK> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Project_Category pc WHERE pc.project = :project")
    void deleteByProject(Project project);
}
