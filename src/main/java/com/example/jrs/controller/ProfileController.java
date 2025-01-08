package com.example.jrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.service.ProfileService;

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

    @PutMapping("/{id}")
    public ProfileSchema updateProfileSchema(@PathVariable long id, @RequestBody ProfileSchema profileSchema) {
        return profileService.updateProfile(id, profileSchema);
    }

    @PostMapping("saveUser")
    public ProfileSchema registerUser(@RequestBody ProfileSchema profileSchema) {
        return profileService.saveProfile(profileSchema);
    }

    @PostMapping("save")
    public ProfileSchema saveUser(@RequestBody ProfileSchema profileSchema) {
        return profileService.saveUserWithSkills(profileSchema);
    }

    @PostMapping("matchingJobsBySkill")
    public List<JobFormSchema> getJobsMachingBySkill(@RequestBody Long userId) {
        return profileService.getJobsMatchingSkill(userId);
    }

}
