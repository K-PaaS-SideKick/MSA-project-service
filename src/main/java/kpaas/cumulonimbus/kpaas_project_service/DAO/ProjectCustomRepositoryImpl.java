package kpaas.cumulonimbus.kpaas_project_service.DAO;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DTO.ProjectSummary;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.QProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kpaas.cumulonimbus.kpaas_project_service.Entity.QProject.project;

@Repository
public class ProjectCustomRepositoryImpl implements ProjectCustomRepository{
    private final JPAQueryFactory queryFactory;
    public ProjectCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<ProjectSummary> findAllWithTitle(String title, Pageable pageable) {
        QProject project = QProject.project;
        JPAQuery<Long> query = queryFactory
                .select(project.count())
                .from(project)
                .where(project.title.containsIgnoreCase(title));

        Long total = query.fetchOne();
        if(total == null)
            throw new EntityNotFoundException("No project found with title " + title);

        List<ProjectSummary> projects = query
                .select(Projections.constructor(ProjectSummary.class, project.pid, project.uid, project.title, project.date, project.upvotes, project.comments, project.views, project.scraps, project.status, project.max_members, project.current_members))
                .from(project)
                .where(project.title.containsIgnoreCase(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(project.date.desc())
                .fetch();

        return new PageImpl<>(projects, pageable, total);
    }

    @Override
    public Page<ProjectSummary> findAllWithPageable(Pageable pageable){
        QProject project = QProject.project;
        JPAQuery<ProjectSummary> query = queryFactory
                .select(Projections.constructor(ProjectSummary.class, project.pid, project.uid, project.title, project.date, project.upvotes, project.comments, project.views, project.scraps, project.status, project.max_members, project.current_members))
                .from(project);
        List<ProjectSummary> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetch().size();

        return new PageImpl<>(results, pageable, total);
    }
}
