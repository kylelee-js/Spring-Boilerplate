package com.example.boilerplate.data.service;

import com.example.boilerplate.data.dto.DataDto;
import com.example.boilerplate.data.entity.DataEntity;
import com.example.boilerplate.data.repository.DataRepository;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DataService {

    private final DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

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
