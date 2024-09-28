package kpaas.cumulonimbus.kpaas_project_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import kpaas.cumulonimbus.kpaas_project_service.DTO.ScrapReturnDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Scraps;
import kpaas.cumulonimbus.kpaas_project_service.Service.ScrapsFacadeService;
import kpaas.cumulonimbus.kpaas_project_service.Service.ScrapsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrapController {


    private final ScrapsFacadeService scrapsFacadeService;
    private final ScrapsService scrapsService;

    public ScrapController(ScrapsFacadeService scrapsFacadeService, ScrapsService scrapsService) {
        this.scrapsFacadeService = scrapsFacadeService;
        this.scrapsService = scrapsService;
    }

    // 게시물 스크랩 하기
    @Operation(summary = "게시물 스크랩하기")
    @PostMapping("/scrap")
    public ResponseEntity<ScrapReturnDTO> scrap(@RequestBody Long pid, @RequestParam String uid) {
        return new ResponseEntity<>(scrapsFacadeService.scrapTransaction(pid, uid), HttpStatus.OK);
    }

    // 게시물 스크랩 취소
    @Operation(summary = "게시물 스크랩 취소")
    @DeleteMapping("/scrap")
    public ResponseEntity<?> deleteScrap(@RequestBody Long pid, @RequestParam String uid) {
        scrapsFacadeService.deleteScrap(pid, uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 내가 스크랩 한 게시물 조회
    @Operation(summary = "내가 스크랩 한 게시물 조회")
    @GetMapping("/scrap")
    public ResponseEntity<List<Long>> getScrap(@RequestParam String uid) {
        return new ResponseEntity<>(scrapsService.getAllScrapsByUid(uid), HttpStatus.OK);
    }
}
