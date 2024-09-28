package kpaas.cumulonimbus.kpaas_project_service.Service;

import kpaas.cumulonimbus.kpaas_project_service.DTO.ScrapReturnDTO;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Scraps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScrapsFacadeService {
    private final ProjectService projectService;
    private final ScrapsService scrapsService;

    public ScrapsFacadeService(ProjectService projectService, ScrapsService scrapsService) {
        this.projectService = projectService;
        this.scrapsService = scrapsService;
    }

    @Transactional
    public ScrapReturnDTO scrapTransaction(Long id, String uid) {
        // 스크랩 생성
        Project project = projectService.findById(id);
        Scraps scraps = Scraps.builder()
                .uid(uid)
                .project(project)
                .build();
        scrapsService.save(scraps);

        // 프로젝트 스크랩 수 증가
        projectService.increaseScraps(id);

        return ScrapReturnDTO.builder()
                .pid(scraps.getProject().getPid())
                .scraps(scraps.getProject().getScraps())
                .build();
    }

    @Transactional
    public void deleteScrap(Long pid, String uid) {
        Scraps scrap = scrapsService.findById(pid, uid);
        scrapsService.delete(scrap);

        // 프로젝트 스크랩 수 감소
        projectService.decreaseScraps(pid);
    }
}
