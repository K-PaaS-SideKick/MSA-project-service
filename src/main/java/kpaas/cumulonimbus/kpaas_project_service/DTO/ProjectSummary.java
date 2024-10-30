package kpaas.cumulonimbus.kpaas_project_service.DTO;

import kpaas.cumulonimbus.kpaas_project_service.Entity.Image;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProjectSummary {
    private Long pid;
    private String uid;
    private String title;
    private String content;
    private LocalDateTime date;
    private int upvotes;
    private int comments;
    private int views;
    private int scraps;
    private String status;
    private int max_members;
    private int current_members;
    private List<Long> images = new ArrayList<>();

    public ProjectSummary(Project project){
        this.pid = project.getPid();
        this.uid = project.getUid();
        this.title = project.getTitle();
        this.content = project.getContent();
        this.date = project.getDate();
        this.upvotes = project.getUpvotes();
        this.comments = project.getComments();
        this.views = project.getViews();
        this.scraps = project.getScraps();
        this.status = project.getStatus();
        this.max_members = project.getMax_members();
        this.current_members = project.getCurrent_members();
        for(Image image : project.getImages()){
            this.images.add(image.getId());
        }
    }
}
