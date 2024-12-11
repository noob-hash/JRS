package com.example.jrs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.service.ProfileService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("jrs/user")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("getAll")
    public List<ProfileSchema> getAllUsers() {
        return profileService.getProfiles();
    }

    @GetMapping("getById")
    public ProfileSchema getProfileById(@RequestParam long id) {
        return profileService.getUser(id);
    }

    @PostMapping("saveUser")
    public ProfileSchema registerUser(@RequestBody ProfileSchema profileSchema) {        
        return profileService.saveProfile(profileSchema);
    }

    @PostMapping("matchingJobsBySkill")
    public List<JobFormSchema> getJobsMachingBySkill(@RequestBody Long userId) {
        return profileService.getJobsMatchingSkill(userId);
    }
    
     
}
