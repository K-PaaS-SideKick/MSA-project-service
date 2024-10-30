package kpaas.cumulonimbus.kpaas_project_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kpaas.cumulonimbus.kpaas_project_service.DTO.*;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class ProjectController {
    private final ProjectService projectService;
    private final TransactionHandler transactionHandler;
    private final ProjectFacadeService projectFacadeService;

    public ProjectController(ProjectService projectService, TransactionHandler transactionHandler, ProjectFacadeService projectFacadeService) {
        this.projectService = projectService;
        this.transactionHandler = transactionHandler;
        this.projectFacadeService = projectFacadeService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //    main page showing list of projects
    @Operation(summary = "프로젝트 메인 페이지", description = "아직 상세한 기능은 미구현")
    @GetMapping("/")
    public ResponseEntity<Page<ProjectSummary>> index(Pageable pageable) {
        return new ResponseEntity<>(projectService.getProjectsWithPageable(pageable), HttpStatus.OK);
    }

    @Operation(summary = "프로젝트 삭제", description = "사용자 아이디 기능 추가해야함")
    @DeleteMapping("/")
    public ResponseEntity<?> deleteProject(@RequestParam Long id) {
        transactionHandler.deleteProjectTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "프로젝트 수정")
    @PatchMapping("/")
    public ResponseEntity<Project> updateProject(@RequestBody UpdateProjectDTO projectDTO) {
        return new ResponseEntity<>(transactionHandler.updateProjectTransaction(projectDTO), HttpStatus.OK);
    }

    @Operation(summary = "프로젝트 상세 보기")
    @GetMapping("/{pid}")
    public ResponseEntity<Project> details(@PathVariable Long pid) {
        Project project = projectService.findById(pid);
        projectService.increaseView(project);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @Operation(summary = "프로젝트 생성", description = "이미지 기능은 아직 미구현. ")
    @PostMapping(path = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjectSummary> createProject(
            @RequestPart(required = false) List<MultipartFile> images,
            @Parameter(description = "application/json 지정해야 등록 됨", content = @Content(mediaType = "application/json"))
            @RequestPart SaveProjectDTO projectDTO) throws IOException {
        return new ResponseEntity<>(transactionHandler.createProjectTransaction(projectDTO, images), HttpStatus.CREATED);
    }

    @Operation(summary = "이미지 보기", description = "이미지 id를 요청")
    @GetMapping(path = "/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] imageBytes = transactionHandler.getImage(id);

        if (imageBytes == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @Operation(summary = "프로젝트 검색", description = "request, response 필드 값 잘 보기")
    @PostMapping("/search")
    public ResponseEntity<Page<ProjectSummary>> search(@RequestParam String titleQuery, Pageable pageable) {
        return new ResponseEntity<>(projectService.showProjects(titleQuery, pageable), HttpStatus.OK);
    }

    // 내 프로젝트 보기
    @Operation(summary = "내 프로젝트 보기", description = "내가 소유한 프로젝트의 모든 프로젝트 id")
    @GetMapping("/my")
    public ResponseEntity<List<Long>> myProject(@RequestParam String uid) {
        return new ResponseEntity<>(projectService.findByUserId(uid), HttpStatus.OK);
    }

    // 프로젝트 참가 신청
    @Operation(summary = "프로젝트 참가 신청", description = "pid를 가지고 있는 프로젝트에 참가하고 싶을 때 사용")
    @PostMapping("/{pid}/join")
    public ResponseEntity<JoinProjectReturnDTO> joinProject(@PathVariable Long pid, @RequestParam String uid) {
        return new ResponseEntity<>(projectFacadeService.joinRequest(pid, uid), HttpStatus.OK);
    }

    // 프로젝트 참가 요청 보기
    @Operation(summary = "프로젝트 참가 요청 보기", description = "pid를 가지는 프로젝트에 대한 모든 참가 요청 보기")
    @GetMapping("/{pid}/join")
    public ResponseEntity<List<JoinRequestDTO>> requests(@PathVariable Long pid, @RequestParam String uid) {
        return new ResponseEntity<>(projectFacadeService.getJoinRequests(pid, uid), HttpStatus.OK);
    }

    // 프로젝트 참가 승인
    @Operation(summary = "프로젝트 참가 승인", description = "pid를 가지는 프로젝트에 requester_id를 가지는 사용자가 참여신청 하였을 때 수락하기")
    @PostMapping("/{pid}/join/{requester_id}")
    public ResponseEntity<JoinProjectReturnDTO> acceptRequest(@PathVariable Long pid, @PathVariable String requester_id, @RequestParam String owner_id) {
        return new ResponseEntity<>(projectFacadeService.acceptJoinRequest(pid, requester_id, owner_id), HttpStatus.OK);
    }

    // 프로젝트 참가 거절, 취소
    @Operation(summary = "프로젝트 참가 거절/취소", description = "pid를 가지는 프로젝트에 참가 요청 거절하기")
    @DeleteMapping("/{pid}/join/{requester_id}")
    public ResponseEntity<?> rejectRequest(@PathVariable Long pid, @PathVariable String requester_id, @RequestParam String owner_id) {
        projectFacadeService.rejectJoinRequest(pid, requester_id, owner_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/kafka")
//    public String kafkatest() {
//        kafkaProducer.send("주문", "사과,1");
//        return "test";
//    }
}
