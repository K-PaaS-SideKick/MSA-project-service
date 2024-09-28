package kpaas.cumulonimbus.kpaas_project_service.Service;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.ProjectRepository;
import kpaas.cumulonimbus.kpaas_project_service.DTO.SaveProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.DTO.UpdateProjectDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.scheduling.annotation.Async;
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
        if(result.isPresent()){
            return result.get();
        }

        else
            throw new EntityNotFoundException();
    }

    public List<Long> findByUserId(String userId) {
        List<Project> projects = projectRepository.findAllByUid(userId);
        List<Long> result = new ArrayList<>();
        for (Project project : projects) {
            result.add(project.getPid());
        }
        return result;
    }

    public boolean isOwnerByPid(Long pid, String uid){
        Project project = findById(pid);
        return project.getUid().equals(uid);
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
    public Project save(Project project){
        return projectRepository.save(project);
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
    public void increaseView(Project project){
        project.setViews(project.getViews() + 1);
        projectRepository.save(project);
    }

    @Transactional
    @Async
    public void increaseComments(Long pid){
        Project project = findById(pid);
        project.setComments(project.getComments() + 1);
        projectRepository.save(project);
    }

    @Transactional
    public void increaseScraps(Long pid){
        Project project = findById(pid);
        project.setScraps(project.getScraps() + 1);
        projectRepository.save(project);
    }

    @Transactional
    public void decreaseScraps(Long pid){
        Project project = findById(pid);
        project.setScraps(project.getScraps() - 1);
        projectRepository.save(project);
    }

    @Transactional
    public void increaseUpvote(Long pid){
        Project project = findById(pid);
        project.setUpvotes(project.getUpvotes() + 1);
        projectRepository.save(project);
    }

    @Transactional
    public void decreaseUpvote(Long pid){
        Project project = findById(pid);
        project.setUpvotes(project.getUpvotes() - 1);
        projectRepository.save(project);
    }

    @Transactional
    public void increaseCurrent_members(Project project){
        if(project.getMax_members() <= project.getCurrent_members())
            throw new IllegalArgumentException("wrong request");
        project.setCurrent_members(project.getCurrent_members() + 1);
        projectRepository.save(project);
    }

    @Transactional
    public void decreaseCurrent_members(Project project){
        if(project.getCurrent_members() >= 0)
            throw new IllegalArgumentException("wrong request");
        project.setCurrent_members(project.getCurrent_members() - 1);
        projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectById(Long id){
        Project result = findById(id);
        projectRepository.delete(result);
    }
}