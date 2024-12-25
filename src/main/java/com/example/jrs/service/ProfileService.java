package com.example.jrs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jrs.dto.RegisterDto;
import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.entity.SkillSchema;
import com.example.jrs.entity.UserAuthSchema;
import com.example.jrs.enums.UserRole;
import com.example.jrs.repo.ProfileSchemeRepo;
import com.example.jrs.repo.SkillsRepo;
import com.example.jrs.repo.UserAuthRepo;

@Service
public class ProfileService implements UserDetailsService {

    @Autowired
    ProfileSchemeRepo profileSchemeRepo;

    @Autowired
    private SkillsRepo skillSchemaRepository;

    @Autowired
    private UserAuthRepo userAuthRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileSchema saveProfile(ProfileSchema profile) {
        return profileSchemeRepo.save(profile);
    }

    public ProfileSchema saveUserWithSkills(ProfileSchema profile) {

        // Save the JobFormSchema first
        List<SkillSchema> skills = profile.getSkills();
        profile.setSkills(null);
        ProfileSchema savedProfile = profileSchemeRepo.save(profile);
        if (skills != null && !skills.isEmpty()) {
            List<SkillSchema> savedSkills = new ArrayList<>();
            for (SkillSchema skill : skills) {
                skill.setProfile(savedProfile); // Set the profile reference
                savedSkills.add(skillSchemaRepository.save(skill));
            }
            savedProfile.setSkills(savedSkills);
        }
        return savedProfile; // Save again with the skills relationship
    }

    public ProfileSchema registerCandidate(RegisterDto registerDto) {
        // First create the UserAuthSchema
        UserAuthSchema userAuth = new UserAuthSchema();
        userAuth.setUsername(registerDto.getUsername());
        userAuth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userAuth.setRole(UserRole.CANDIDATE);
        userAuth.setActive(true);

        // Create and set up the ProfileSchema
        ProfileSchema profile = registerDto.getProfileData();
        profile.setUserAuthSchema(userAuth);

        // Handle skills if present
        List<SkillSchema> skills = profile.getSkills();
        profile.setSkills(null);

        // Save the profile with auth data
        ProfileSchema savedProfile = profileSchemeRepo.save(profile);

        // Save skills if present
        if (skills != null && !skills.isEmpty()) {
            List<SkillSchema> savedSkills = new ArrayList<>();
            for (SkillSchema skill : skills) {
                skill.setProfile(savedProfile);
                savedSkills.add(skillSchemaRepository.save(skill));
            }
            savedProfile.setSkills(savedSkills);
        }

        return savedProfile;
    }

    public List<ProfileSchema> getProfiles() {
        return profileSchemeRepo.findAll();
    }

    public ProfileSchema getUser(long id) {
        return profileSchemeRepo.findById(id).orElse(null);
    }

    public List<JobFormSchema> getJobsMatchingSkill(long userId) {
        return profileSchemeRepo.getJobsMatchingSkill(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ProfileSchema profile = profileSchemeRepo.findByUserAuthSchema_Username(username);

        if (profile == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserAuthSchema userAuth = profile.getUserAuthSchema();

        if (!userAuth.isActive()) {
            throw new RuntimeException("User account is disabled");
        }

        return User.builder()
                .username(profile.getUserAuthSchema().getUsername())
                .password(profile.getUserAuthSchema().getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + userAuth.getRole().name()))
                .disabled(!userAuth.isActive())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                // .roles("USER")
                .build();
    }
}
