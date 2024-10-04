package kpaas.cumulonimbus.kpaas_project_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveProjectDTO {
    private String uid;

    private String title;

    private String content;

    private String status;

    private String repo_link;

    private List<Integer> category;

    private int current_members;

    private int max_members;

//    @Nullable
//    private List<MultipartFile> images = new ArrayList<>();
}
