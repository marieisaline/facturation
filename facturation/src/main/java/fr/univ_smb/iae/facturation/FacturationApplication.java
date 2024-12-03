package fr.univ_smb.iae.facturation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FacturationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FacturationApplication.class, args);
    }

    /**
     * Configure un bean RestTemplate pour permettre la communication HTTP avec d'autres microservices.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
