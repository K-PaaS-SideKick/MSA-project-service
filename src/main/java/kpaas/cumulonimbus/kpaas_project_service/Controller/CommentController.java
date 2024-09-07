package kpaas.cumulonimbus.kpaas_project_service.Controller;

import kpaas.cumulonimbus.kpaas_project_service.DTO.GetCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.NewCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Service.CommentService;
import kpaas.cumulonimbus.kpaas_project_service.Service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final ProjectService projectService;

    public CommentController(CommentService commentService, ProjectService projectService) {
        this.commentService = commentService;
        this.projectService = projectService;
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody NewCommentDTO commentDTO) {
        Comment comment = commentService.saveComment(commentDTO);
        projectService.increaseComments(commentDTO.getPid());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam Long cid) {
        commentService.deleteByCid(cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // project의 댓글 모두 불러오기
    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam Long pid) {
        Project project = projectService.findById(pid);
        return new ResponseEntity<>(commentService.getCommentsByPid(project), HttpStatus.OK);
    }
}
