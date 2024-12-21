package com.example.springgongbu.demo.data.repository;


import com.example.springgongbu.demo.data.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {
    // TODO: Implement DataRepository
}
