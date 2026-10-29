package ar.edu.unju.fi.arquitecturas.courierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CourierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourierServiceApplication.class, args);
    }

}
