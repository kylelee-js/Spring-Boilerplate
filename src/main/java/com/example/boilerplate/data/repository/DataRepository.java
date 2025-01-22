package com.example.boilerplate.data.repository;


import com.example.boilerplate.data.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {
    // TODO: Implement DataRepository
}

