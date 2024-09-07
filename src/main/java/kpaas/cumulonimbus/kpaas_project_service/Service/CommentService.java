package kpaas.cumulonimbus.kpaas_project_service.Service;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.CommentRepository;
import kpaas.cumulonimbus.kpaas_project_service.DTO.CommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.GetCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.NewCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.SubCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            comment.setParentComment(parentComment);
        }

        Project parentProject = projectService.findById(commentDTO.getPid());
        comment.setParentPid(parentProject);

        return commentRepository.save(comment);
    }

    public Comment findByCid(Long cid) {
        commentRepository.findByCid(cid);

        if (commentRepository.findByCid(cid) != null)
            return commentRepository.findByCid(cid);

        else throw new EntityNotFoundException();
    }

    @Transactional
    public void deleteByCid(Long cid) {
        // 지우고자 하는 댓글
        Comment comment = findByCid(cid);

        // 대댓글 지우기
        commentRepository.deleteByParentComment(comment);

        // 댓글 지우기
        commentRepository.delete(comment);
    }

    public GetCommentDTO getCommentsByPid(Project project) {
        GetCommentDTO getCommentDTO = new GetCommentDTO();
        getCommentDTO.setPid(project.getPid());

        List<Comment> foundComments = commentRepository.findAllByParentPid(project);

        // response에 댓글 추가
        for (Comment comment : foundComments) {
            if(comment.getParentComment() == null){
                CommentDTO commentDTO = CommentDTO.builder()
                        .cid(comment.getCid())
                        .content(comment.getContent())
                        .created_at(comment.getCreated_at())
                        .build();
                getCommentDTO.getComments().add(commentDTO);
            }
        }

        // response에 대댓글 추가
        for (Comment comment : foundComments) {
            if(comment.getParentComment() != null){
                Long parentCid = comment.getParentComment().getCid();
                for (CommentDTO commentDto : getCommentDTO.getComments()) {
                    if(commentDto.getCid().equals(parentCid)){
                        SubCommentDTO subCommentDTO = SubCommentDTO.builder()
                                .cid(comment.getCid())
                                .content(comment.getContent())
                                .created_at(comment.getCreated_at())
                                .uid(comment.getUid())
                                .build();
                        commentDto.getSubComments().add(subCommentDTO);
                    }
                }
            }
        }

        return getCommentDTO;
    }

    @Transactional
    public void deleteByPid(Project project) {
        commentRepository.deleteAllByParentPid(project);
    }
}
