package com.example.jrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jrs.entity.SkillSchema;
import com.example.jrs.repo.SkillsRepo;

@Service
public class SkillsServices {
    @Autowired
    SkillsRepo skillSchemeRepo;

    public SkillSchema saveSkill(SkillSchema skill) {
        return skillSchemeRepo.save(skill);
    }

    public List<SkillSchema> getSkills(){
        return skillSchemeRepo.findAll();
    }

    public SkillSchema getSkill(long id){
        return skillSchemeRepo.findById(id).orElse(null);
    }

}
