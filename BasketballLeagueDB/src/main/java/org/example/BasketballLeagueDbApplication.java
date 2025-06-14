package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("entity")
public class BasketballLeagueDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasketballLeagueDbApplication.class, args);
    }
}