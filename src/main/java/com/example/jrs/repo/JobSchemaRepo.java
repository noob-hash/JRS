package com.example.jrs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jrs.entity.JobFormSchema;

@Repository
public interface JobSchemaRepo extends JpaRepository<JobFormSchema, Long> {

}
