package kpaas.cumulonimbus.kpaas_project_service.Service;

import kpaas.cumulonimbus.kpaas_project_service.DTO.UpvoteReturnDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Upvotes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpvoteFacadeService {

    private final UpvotesService upvotesService;
    private final ProjectService projectService;

    public UpvoteFacadeService(UpvotesService upvotesService, ProjectService projectService) {
        this.upvotesService = upvotesService;
        this.projectService = projectService;
    }

    @Transactional
    public UpvoteReturnDTO createUpvote(Long pid, String uid){
        Upvotes upvotes = upvotesService.createUpvote(pid, uid);
        projectService.increaseUpvote(pid);

        return UpvoteReturnDTO.builder()
                .upvotes(upvotes.getPid().getUpvotes())
                .pid(upvotes.getPid().getPid())
                .build();
    }

    @Transactional
    public void deleteUpvote(Long pid, String uid){
        upvotesService.deleteUpvote(projectService.findById(pid), uid);
        projectService.decreaseUpvote(pid);
    }
}
