package com.example.feedbacktoolbackend;
/*
  @Author Sven Molenaar
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FeedbacktoolBackendApplication {
    /**
     * The main entry point for starting the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application (not used here).
     * @author Sven Molenaar
     */

    public static void main(String[] args) {
        SpringApplication.run(FeedbacktoolBackendApplication.class, args);
    }

}
