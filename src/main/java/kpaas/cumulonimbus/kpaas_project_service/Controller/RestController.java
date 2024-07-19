package kpaas.cumulonimbus.kpaas_project_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
