package kpaas.cumulonimbus.kpaas_project_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import kpaas.cumulonimbus.kpaas_project_service.DTO.GetCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.NewCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Scraps;
import kpaas.cumulonimbus.kpaas_project_service.Service.CommentService;
import kpaas.cumulonimbus.kpaas_project_service.Service.ProjectService;
import kpaas.cumulonimbus.kpaas_project_service.Service.TransactionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final ProjectService projectService;
    private final TransactionHandler transactionHandler;

    public CommentController(CommentService commentService, ProjectService projectService, TransactionHandler transactionHandler) {
        this.commentService = commentService;
        this.projectService = projectService;
        this.transactionHandler = transactionHandler;
    }

    @Operation(summary = "댓글 달기")
    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@RequestBody NewCommentDTO commentDTO) {
        Comment comment = transactionHandler.newCommentTransaction(commentDTO);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam Long cid) {
        commentService.deleteByCid(cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // project의 댓글 모두 불러오기
    @Operation(summary = "프로젝트의 댓글 모두 불러오기", description = "댓글 계층 구초 유의해서 사용")
    @GetMapping("/comment")
    public ResponseEntity<GetCommentDTO> getComment(@RequestParam Long pid) {
        Project project = projectService.findById(pid);
        return new ResponseEntity<>(commentService.getCommentsByPid(project), HttpStatus.OK);
    }
}
