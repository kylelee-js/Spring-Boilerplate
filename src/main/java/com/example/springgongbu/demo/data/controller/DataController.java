package com.example.springgongbu.demo.data.controller;

import com.example.springgongbu.demo.data.entity.DataEntity;
import com.example.springgongbu.demo.data.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/data")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private DataService dataService;

    @GetMapping("/home")
    public String home() {
        logger.info("DataController.home() called.");
        return "home";
    }

    @GetMapping("/test")
    public String test() {
        logger.info("DataController.test() called.");
        return "test";
    }

    @GetMapping("/test2")
    public String test2() {
        logger.info("DataController.test2() called.");
        dataService.test();
        return "test2";
    }

    @GetMapping("/getData")
    public List<DataEntity> getData() {
        logger.info("DataController.getData() called.");
        return dataService.getData();
    }
}
