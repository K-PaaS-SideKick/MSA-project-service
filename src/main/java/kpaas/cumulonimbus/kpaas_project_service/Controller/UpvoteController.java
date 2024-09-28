package kpaas.cumulonimbus.kpaas_project_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import kpaas.cumulonimbus.kpaas_project_service.DTO.UpvoteReturnDTO;
import kpaas.cumulonimbus.kpaas_project_service.Service.UpvoteFacadeService;
import kpaas.cumulonimbus.kpaas_project_service.Service.UpvotesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpvoteController {

    private final UpvoteFacadeService upvoteFacadeService;
    private final UpvotesService upvotesService;

    public UpvoteController(UpvoteFacadeService upvoteFacadeService, UpvotesService upvotesService) {
        this.upvoteFacadeService = upvoteFacadeService;
        this.upvotesService = upvotesService;
    }

    // upvote 하기
    @Operation(summary = "upvote 하기")
    @PostMapping("/upvote")
    public ResponseEntity<UpvoteReturnDTO> upvote(@RequestParam Long pid, @RequestParam String uid) {
        return new ResponseEntity<>(upvoteFacadeService.createUpvote(pid, uid), HttpStatus.OK);
    }

    // upvote 취소
    @Operation(summary = "upvote 취소")
    @DeleteMapping("/upvote")
    public ResponseEntity<?> deleteUpvote(@RequestParam Long pid, @RequestParam String uid) {
        upvoteFacadeService.deleteUpvote(pid, uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 사용자가 게시물에 upvote 했는지 판별
    @Operation(summary = "사용자가 게시물에 upvote 했는지 판별")
    @GetMapping("/upvote")
    public ResponseEntity<Boolean> getUpvote(@RequestParam Long pid, @RequestParam String uid) {
        return new ResponseEntity<>(upvotesService.checkUpvote(pid, uid), HttpStatus.OK);
    }
}
