package com.example.springgongbu.demo.data.service;

import com.example.springgongbu.demo.data.entity.DataEntity;
import com.example.springgongbu.demo.data.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    public String home() {
        return "home";
    }

    public String test() {
        DataEntity dataEntity = new DataEntity();
        dataEntity.setName("kyle");
        dataEntity.setAge("20");
        dataEntity.setEmail("email");
        dataEntity.setAddress("address");
        dataEntity.setPhoneNumber("phoneNumber");
        dataRepository.save(dataEntity);
        return "test";
    }

    public List<DataEntity> getData() {
        return dataRepository.findAll();
    }
}
