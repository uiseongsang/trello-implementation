package com.winner.trelloimplementation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class TrelloImplementationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloImplementationApplication.class, args);
    }

}
