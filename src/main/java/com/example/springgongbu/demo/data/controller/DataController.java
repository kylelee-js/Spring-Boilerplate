package com.example.springgongbu.demo.data.controller;

import com.example.springgongbu.demo.data.dto.DataDto;
import com.example.springgongbu.demo.data.entity.DataEntity;
import com.example.springgongbu.demo.data.service.DataService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping("/create")
    public String create(@RequestBody @Valid DataDto dto) {
        log.info("DataController.created() called.");
        String text = dataService.create(dto);
        return text;
    }

    @GetMapping("/getAll")
    public List<DataEntity> getAll() {
        log.info("DataController.getAll() called.");
        return dataService.getAll();
    }
}
