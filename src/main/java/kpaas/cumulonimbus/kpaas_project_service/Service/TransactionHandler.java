package kpaas.cumulonimbus.kpaas_project_service.Service;

import jakarta.persistence.EntityNotFoundException;
import kpaas.cumulonimbus.kpaas_project_service.DTO.*;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Comment;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Scraps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TransactionHandler {
    private final CommentService commentService;
    private final ProjectService projectService;
    private final ProjectCategoryService categoryService;
    private final ScrapsService scrapsService;

    public TransactionHandler(CommentService commentService, ProjectService projectService, ProjectCategoryService categoryService, ScrapsService scrapsService) {
        this.commentService = commentService;
        this.projectService = projectService;
        this.categoryService = categoryService;
        this.scrapsService = scrapsService;
    }

    @Transactional
    public Comment newCommentTransaction(NewCommentDTO commentDTO){
        Comment comment = commentService.saveComment(commentDTO);
        projectService.increaseComments(commentDTO.getPid());
        return comment;
    }

    @Transactional
    public void deleteProjectTransaction(Long id){
        Project deleteProject = projectService.findById(id);
        categoryService.deleteProjectCategoryByProject(deleteProject);
        commentService.deleteByPid(deleteProject);
        projectService.deleteProjectById(id);
    }

    @Transactional
    public ProjectSummary createProjectTransaction(SaveProjectDTO projectDTO, List<MultipartFile> images) throws IOException {
        Project newProject = projectService.saveNewProject(projectDTO);
        categoryService.saveProjectCategories(newProject, projectDTO.getCategory());
        ProjectSummary summary = new ProjectSummary(newProject);
        summary.setCategory(categoryService.getProjectCategory(newProject));
        return summary;
    }

//    public byte[] getImage(Long id){
//        return imageService.getImageById(id);
//    }

    @Transactional
    public ProjectDetail updateProjectTransaction(UpdateProjectDTO projectDTO){
        Project project = projectService.findById(projectDTO.getPid());
        if (project == null) throw new EntityNotFoundException();

        // 카테고리 삭제 후 재생성
        categoryService.deleteProjectCategoryByProject(project);
        categoryService.saveProjectCategories(project, projectDTO.getCategory());

        Project updatedProject = projectService.updateProject(project, projectDTO);
        ProjectDetail detail = new ProjectDetail(updatedProject);
        detail.setCategories(categoryService.getProjectCategory(updatedProject));
        return detail;
    }
}
