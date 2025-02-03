package dev.davletshin.dossier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableKafka
@EnableFeignClients
public class DossierApplication {

    public static void main(String[] args) {
        SpringApplication.run(DossierApplication.class, args);
    }

}
