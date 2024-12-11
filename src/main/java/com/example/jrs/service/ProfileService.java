package com.example.jrs.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.repo.ProfileSchemeRepo;

@Service
public class ProfileService implements UserDetailsService {

    @Autowired
    ProfileSchemeRepo profileSchemeRepo;

    public ProfileSchema saveProfile(ProfileSchema profile) {
        return profileSchemeRepo.save(profile);
    }

    public List<ProfileSchema> getProfiles(){
        return profileSchemeRepo.findAll();
    }

    public ProfileSchema getUser(long id){
        return profileSchemeRepo.findById(id).orElse(null);
    }
    
    public List<JobFormSchema> getJobsMatchingSkill(long userId){
        return profileSchemeRepo.getJobsMatchingSkill(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ProfileSchema profile = profileSchemeRepo.findByUserAuthSchema_Username(username);

        if (profile == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        return User.builder()
                .username(profile.getUserAuthSchema().getUsername()) 
                .password(profile.getUserAuthSchema().getPassword())
                .roles("USER")
                .build();
    }
}
