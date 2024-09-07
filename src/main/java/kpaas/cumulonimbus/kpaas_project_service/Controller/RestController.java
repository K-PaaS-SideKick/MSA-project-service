package kpaas.cumulonimbus.kpaas_project_service.Controller;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DTO.NewCommentDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.SaveProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.SearchDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.UpdateProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Service.CommentService;
import kpaas.cumulonimbus.kpaas_project_service.Service.ProjectCategoryService;
import kpaas.cumulonimbus.kpaas_project_service.Service.ProjectService;
import kpaas.cumulonimbus.kpaas_project_service.kafka.KafkaProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final KafkaProducer kafkaProducer;
    private final ProjectService projectService;
    private final CommentService commentService;
    private final ProjectCategoryService categoryService;

    public RestController(KafkaProducer kafkaProducer, ProjectService projectService, CommentService commentService, ProjectCategoryService categoryService) {
        this.kafkaProducer = kafkaProducer;
        this.projectService = projectService;
        this.commentService = commentService;
        this.categoryService = categoryService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

//    main page showing list of projects
    @GetMapping("/")
    public List<MappingJacksonValue> index(){
        return projectService.showProjects("index");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteProject(@RequestParam Long id) {
        Project deleteProject = projectService.findById(id);
        categoryService.deleteProjectCategoryByProject(deleteProject);
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/")
    public Project updateProject(@RequestBody UpdateProjectDTO projectDTO) {
        Project project = projectService.findById(projectDTO.getPid());
        if (project == null) throw new EntityNotFoundException();

        // 카테고리 삭제 후 재생성
        categoryService.deleteProjectCategoryByProject(project);
        categoryService.saveProjectCategories(project, projectDTO.getCategory());

        return projectService.updateProject(project, projectDTO);
    }

    @GetMapping("/details")
    public ResponseEntity<?> details(@RequestParam Long pid) {
        Project project = projectService.findById(pid);
        if (project == null) throw new EntityNotFoundException();

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping(path = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Project createProject(@ModelAttribute SaveProjectDTO projectDTO) {

        Project newProject = projectService.saveNewProject(projectDTO);
        categoryService.saveProjectCategories(newProject, projectDTO.getCategory());
        return newProject;
    }

    @PostMapping("/search")
    public List<MappingJacksonValue> search(@RequestBody SearchDTO searchDTO){
        return projectService.showProjects("");
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody NewCommentDTO commentDTO) {
        Comment comment = commentService.saveComment(commentDTO);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }



    @GetMapping("/kafka")
    public String kafkatest() {
        kafkaProducer.send("주문", "사과,1");
        return "test";
    }
}
