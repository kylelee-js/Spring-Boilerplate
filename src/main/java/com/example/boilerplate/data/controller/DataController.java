package com.example.boilerplate.data.controller;

import com.example.boilerplate.data.dto.DataDto;
import com.example.boilerplate.data.entity.DataEntity;
import com.example.boilerplate.data.service.DataService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {

    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

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
