package kpaas.cumulonimbus.kpaas_project_service.Service;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DAO.ProjectRepository;
import kpaas.cumulonimbus.kpaas_project_service.DAO.ScrapsRepository;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Scraps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapsService {
    private final ScrapsRepository scrapsRepository;
    private final ProjectRepository projectRepository;

    public ScrapsService(ScrapsRepository scrapsRepository, ProjectRepository projectRepository) {
        this.scrapsRepository = scrapsRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Scraps save(Scraps scraps) {
        return scrapsRepository.save(scraps);
    }

    public Scraps findById(Long pid, String uid){
        Project project;
        if(projectRepository.findById(pid).isPresent()){
            project = projectRepository.findById(pid).get();
        }
        else{
            throw new EntityNotFoundException("Project with id " + pid + " not found");
        }

        if(scrapsRepository.findByProjectAndUid(project, uid).isPresent()){
            return scrapsRepository.findByProjectAndUid(project, uid).get();
        }
        else {
            throw new EntityNotFoundException("Scraps not found");
        }
    }

    @Transactional
    void delete(Scraps scraps) {
        scrapsRepository.delete(scraps);
    }

    public List<Long> getAllScrapsByUid(String uid){
        List<Scraps> scrapsList = scrapsRepository.findAllByUid(uid);
        List<Long> scrapsIds = new ArrayList<>();
        for(Scraps scraps : scrapsList){
            scrapsIds.add(scraps.getProject().getPid());
        }
        return scrapsIds;
    }
}
