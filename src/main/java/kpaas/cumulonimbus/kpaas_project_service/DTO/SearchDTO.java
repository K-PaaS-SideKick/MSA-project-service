package kpaas.cumulonimbus.kpaas_project_service.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public
class SearchDTO {
    List<String> category;
    String query;
    Integer page;
    String sortby;
}
