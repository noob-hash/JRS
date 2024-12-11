package com.example.jrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.entity.SkillSchema;
import com.example.jrs.service.SkillsServices;

@RestController
@RequestMapping("jrs/skill")
public class SkillController {
    @Autowired
    SkillsServices skillService;

    @GetMapping("getAll")
    public List<SkillSchema> getAllUsers() {
        return skillService.getSkills();
    }

    @GetMapping("getById")
    public SkillSchema getSkillById(@RequestParam long id) {
        return skillService.getSkill(id);
    }

    @PostMapping("saveSkill")
    public SkillSchema registerUser(@RequestBody SkillSchema skillSchema) {        
        return skillService.saveSkill(skillSchema);
    }
}
