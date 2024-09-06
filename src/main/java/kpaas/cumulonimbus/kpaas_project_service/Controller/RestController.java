package kpaas.cumulonimbus.kpaas_project_service.Controller;

import kpaas.cumulonimbus.kpaas_project_service.DTO.SaveProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.SearchDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
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
    private final ProjectCategoryService categoryService;

    public RestController(KafkaProducer kafkaProducer, ProjectService projectService, ProjectCategoryService categoryService) {
        this.kafkaProducer = kafkaProducer;
        this.projectService = projectService;
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
    @Transactional
    public ResponseEntity<?> deleteProject(@RequestParam Long id) {
        Project deleteProject = projectService.findById(id);
        categoryService.deleteProjectCategoryByProject(deleteProject);
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public Project createProject(@ModelAttribute SaveProjectDTO projectDTO) {

        Project newProject = projectService.saveNewProject(projectDTO);
        categoryService.saveProjectCategories(newProject, projectDTO.getCategory());
        return newProject;
    }

    @PostMapping("/search")
    public List<MappingJacksonValue> search(SearchDTO searchDTO){
        return projectService.showProjects("");
    }

    @GetMapping("/kafka")
    public String kafkatest() {
        kafkaProducer.send("주문", "사과,1");
        return "test";
    }
}
