package kpaas.cumulonimbus.kpaas_project_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KPaasProjectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KPaasProjectServiceApplication.class, args);
    }

}
