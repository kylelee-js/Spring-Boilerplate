package com.example.springgongbu.demo.data.controller;

import com.example.springgongbu.demo.data.dto.DataDto;
import com.example.springgongbu.demo.data.entity.DataEntity;
import com.example.springgongbu.demo.data.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/data")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private DataService dataService;

    @PostMapping("/create")
    public String create(@RequestBody DataDto dto) {
        logger.info("DataController.created() called.");
        String text = dataService.create(dto);
        return text;
    }

    @GetMapping("/getAll")
    public List<DataEntity> getAll() {
        logger.info("DataController.getAll() called.");
        return dataService.getAll();
    }
}
