package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateProjectDTO {
    private Long pid;

    private String uid;

    private String title;

    private String content;

    private String status;

    private String repo_link;

    private List<Integer> category;

    private int current_members;

    private int max_members;

    private List<MultipartFile> images;
}
