package com.example.springgongbu.demo.data.service;

import com.example.springgongbu.demo.data.dto.DataDto;
import com.example.springgongbu.demo.data.entity.DataEntity;
import com.example.springgongbu.demo.data.repository.DataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    @Transactional
    public String create(DataDto dto) {
        log.debug("DataService.create() called : {}", dto.toString());
        Random random = new Random();
        DataEntity dataEntity = new DataEntity();
        dataEntity.setName(dto.getName());
        dataEntity.setAge(random.nextInt(100));
        dataEntity.setEmail("email");
        dataEntity.setAddress("address");
        dataEntity.setPhoneNumber("phoneNumber");
        dataRepository.save(dataEntity);

        return "created";
    }

    public List<DataEntity> getAll() {
        return dataRepository.findAll();
    }
}
