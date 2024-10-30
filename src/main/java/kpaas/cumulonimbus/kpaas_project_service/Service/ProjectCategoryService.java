package kpaas.cumulonimbus.kpaas_project_service.Service;

import kpaas.cumulonimbus.kpaas_project_service.DAO.ProjectCategoryRepository;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project_Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectCategoryService {
    private final ProjectCategoryRepository projectCategoryRepository;

    public ProjectCategoryService(ProjectCategoryRepository projectCategoryRepository) {
        this.projectCategoryRepository = projectCategoryRepository;
    }

    @Transactional
    public void saveProjectCategories(Project project, List<Integer> categories){
        for(Integer categoryId : categories){
            Project_Category projectCategory = Project_Category.builder()
                    .project(project)
                    .category(categoryId)
                    .build();
            projectCategoryRepository.save(projectCategory);
        }
    }

    @Transactional
    public void deleteProjectCategoryByProject(Project project){
        projectCategoryRepository.deleteByProject(project);
    }

    public List<Integer> getProjectCategory(Project project){
        List<Project_Category> categories = projectCategoryRepository.findAllByProject(project);
        List<Integer> categoryList = new ArrayList<>();
        for(Project_Category category : categories){
            categoryList.add(category.getCategory());
        }
        return categoryList;
    }
}
