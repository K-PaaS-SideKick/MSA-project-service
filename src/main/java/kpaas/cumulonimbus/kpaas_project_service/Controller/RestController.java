package kpaas.cumulonimbus.kpaas_project_service.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}
