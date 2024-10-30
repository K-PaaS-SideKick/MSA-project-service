package kpaas.cumulonimbus.kpaas_project_service.DTO;

import jakarta.persistence.Column;
import kpaas.cumulonimbus.kpaas_project_service.Entity.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProjectDetail {
    private Long pid;

    private String uid;

    private String title;

    private LocalDateTime date;

    private String content;

    private String repo_link;

    private int upvotes;

    private int comments;

    private int views;

    private  int scraps;

    private String status;

    private int max_members;

    private int current_members;

    private List<Integer> categories;

    public ProjectDetail(Project project) {
        this.pid = project.getPid();
        this.uid = project.getUid();
        this.title = project.getTitle();
        this.date = project.getDate();
        this.content = project.getContent();
        this.repo_link = project.getRepo_link();
        this.upvotes = project.getUpvotes();
        this.comments = project.getComments();
        this.views = project.getViews();
        this.scraps = project.getScraps();
        this.status = project.getStatus();
        this.max_members = project.getMax_members();
        this.current_members = project.getCurrent_members();
        this.categories = new ArrayList<>();
    }
}
