package com.example.jrs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jrs.entity.SkillSchema;

@Repository
public interface SkillsRepo extends JpaRepository<SkillSchema, Long> {

}
