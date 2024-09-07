package kpaas.cumulonimbus.kpaas_project_service.Service;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.CommentRepository;
import kpaas.cumulonimbus.kpaas_project_service.DTO.NewCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProjectService projectService;

    public CommentService(CommentRepository commentRepository, ProjectService projectService) {
        this.commentRepository = commentRepository;
        this.projectService = projectService;
    }

    @Transactional
    public Comment saveComment(NewCommentDTO commentDTO) {
        LocalDateTime now = LocalDateTime.now();

        Comment comment = Comment.builder()
                .uid(commentDTO.getUid())
                .content(commentDTO.getComment())
                .created_at(now)
                .build();
        if (commentDTO.getParent_comment() != null) {
            Comment parentComment = findByCid(commentDTO.getParent_comment());
            comment.setParent_comment(parentComment);
        }

        Project parentProject = projectService.findById(commentDTO.getPid());
        comment.setParent_pid(parentProject);

        return commentRepository.save(comment);
    }

    public Comment findByCid(Long cid) {
        commentRepository.findByCid(cid);

        if (commentRepository.findByCid(cid) != null)
            return commentRepository.findByCid(cid);

        else throw new EntityNotFoundException();
    }
}
