package kpaas.cumulonimbus.kpaas_project_service.Service;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.ProjectRepository;
import kpaas.cumulonimbus.kpaas_project_service.DTO.SaveProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.UpdateProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project findById(Long id) {
        Optional<Project> result = projectRepository.findById(id);
        if(result.isPresent())
            return result.get();
        else
            throw new EntityNotFoundException();
    }

    public List<MappingJacksonValue> showProjects(String option){
        List<Project> projects = new ArrayList<>();
        if(option.equals("index")){
            projects = projectRepository.findAll();
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("content");
        SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("ContentFilter", filter);

        List<MappingJacksonValue> resultProjects = new ArrayList<>();
        for(Project project : projects){
            MappingJacksonValue value = new MappingJacksonValue(project);
            resultProjects.add(value);
        }
        return resultProjects;
    }

    @Transactional
    public Project saveNewProject(SaveProjectDTO projectDTO){
        LocalDateTime now = LocalDateTime.now();

        Project project = Project.builder()
                .uid(projectDTO.getUid())
                .title(projectDTO.getTitle())
                .date(now)
                .content(projectDTO.getContent())
                .upvotes(0)
                .comments(0)
                .views(0)
                .scraps(0)
                .status(projectDTO.getStatus())
                .max_members(projectDTO.getMax_members())
                .current_members(projectDTO.getCurrent_members())
                .repo_link(projectDTO.getRepo_link())
                .build();

        projectRepository.save(project);

        return project;
    }

    @Transactional
    public Project updateProject(Project project, UpdateProjectDTO projectDTO){
        project.setContent(projectDTO.getContent());
        project.setTitle(projectDTO.getTitle());
        project.setCurrent_members(projectDTO.getCurrent_members());
        project.setMax_members(projectDTO.getMax_members());
        project.setRepo_link(projectDTO.getRepo_link());
        project.setStatus(projectDTO.getStatus());

        projectRepository.save(project);
        return project;
    }

    @Transactional
    public void deleteProjectById(Long id){
        Project result = findById(id);
        projectRepository.delete(result);
    }
}