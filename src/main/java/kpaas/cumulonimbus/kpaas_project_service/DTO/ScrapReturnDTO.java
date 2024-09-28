package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapReturnDTO {
    private Long pid;
    private Integer scraps;
}
