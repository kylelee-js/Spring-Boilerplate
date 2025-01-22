package com.example.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BoilerplateApplication {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BoilerplateApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BoilerplateApplication.class, args);
    }
}

