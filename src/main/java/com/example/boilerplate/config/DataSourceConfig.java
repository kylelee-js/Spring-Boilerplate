package com.example.boilerplate.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @PostConstruct
    public void logDatasourceUrl() {
        System.out.println("Datasource URL: " + datasourceUrl);
    }
}