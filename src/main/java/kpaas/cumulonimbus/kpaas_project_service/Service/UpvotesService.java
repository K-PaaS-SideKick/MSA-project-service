package kpaas.cumulonimbus.kpaas_project_service.Service;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.ProjectRepository;
import kpaas.cumulonimbus.kpaas_project_service.DAO.UpvotesRepository;
import kpaas.cumulonimbus.kpaas_project_service.DTO.UpvoteReturnDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Upvotes;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpvotesService {
    private final UpvotesRepository upvotesRepository;
    private final ProjectRepository projectRepository;

    public UpvotesService(UpvotesRepository upvotesRepository, ProjectRepository projectRepository) {
        this.upvotesRepository = upvotesRepository;
        this.projectRepository = projectRepository;
    }

    public Upvotes createUpvote(Long pid, String uid){
        Optional<Project> projectOptional = projectRepository.findById(pid);
        Project project;
        if(projectOptional.isPresent()){
            project = projectOptional.get();
        }
        else {
            throw new EntityNotFoundException("Project not found");
        }
        Upvotes upvotes = Upvotes.builder()
                .pid(project)
                .uid(uid)
                .build();
        return upvotesRepository.save(upvotes);
    }

    public void deleteUpvote(Project project, String uid){
        upvotesRepository.deleteByPidAndUid(project, uid);
    }

    public boolean checkUpvote(Long pid, String uid){
        Optional<Project> projectOptional = projectRepository.findById(pid);
        Project project;
        if(projectOptional.isPresent()){
            project = projectOptional.get();
        }
        else {
            throw new EntityNotFoundException("Project not found");
        }
        return upvotesRepository.existsByPidAndUid(project, uid);
    }
}
