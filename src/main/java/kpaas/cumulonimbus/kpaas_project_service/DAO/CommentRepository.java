package kpaas.cumulonimbus.kpaas_project_service.DAO;

import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCid(Long cid);
    List<Comment> findAllByParentPid(Project project);
    void deleteByParentComment(Comment comment);
    void deleteAllByParentPid(Project project);
}
