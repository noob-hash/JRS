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

import jakarta.validation.ValidationException;

@Service
public class ProfileService implements UserDetailsService {

    @Autowired
    ProfileSchemeRepo profileSchemeRepo;

    @Autowired
    private SkillsRepo skillSchemaRepository;

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

    // public ProfileSchema registerCandidate(RegisterDto registerDto) {
    // // First create the UserAuthSchema
    // UserAuthSchema userAuth = new UserAuthSchema();
    // userAuth.setUsername(registerDto.getUsername());
    // userAuth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    // userAuth.setRole(UserRole.CANDIDATE);
    // userAuth.setActive(true);

    // // Create and set up the ProfileSchema
    // ProfileSchema profile = registerDto.getProfileData();
    // profile.setUserAuthSchema(userAuth);

    // // Handle skills if present
    // List<SkillSchema> skills = profile.getSkills();
    // profile.setSkills(null);

    // // Save the profile with auth data
    // ProfileSchema savedProfile = profileSchemeRepo.save(profile);

    // // Save skills if present
    // if (skills != null && !skills.isEmpty()) {
    // List<SkillSchema> savedSkills = new ArrayList<>();
    // for (SkillSchema skill : skills) {
    // skill.setProfile(savedProfile);
    // savedSkills.add(skillSchemaRepository.save(skill));
    // }
    // savedProfile.setSkills(savedSkills);
    // }

    // return savedProfile;
    // }

    public ProfileSchema registerCandidate(RegisterDto registerDto) {
        // Validate basic required fields
        if (registerDto.getUsername() == null || registerDto.getPassword() == null) {
            throw new ValidationException("Username and password are required");
        }

        // Create UserAuthSchema with basic auth info
        UserAuthSchema userAuth = new UserAuthSchema();
        userAuth.setUsername(registerDto.getUsername());
        userAuth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userAuth.setRole(UserRole.CANDIDATE);
        userAuth.setActive(true);

        // Create ProfileSchema with minimal required information
        ProfileSchema profile = new ProfileSchema();
        if (registerDto.getProfileData() != null) {
            profile.setName(registerDto.getProfileData().getName());
            profile.setEmail(registerDto.getProfileData().getEmail());
        }
        profile.setUserAuthSchema(userAuth);

        // Save the basic profile
        return profileSchemeRepo.save(profile);
    }

    public ProfileSchema completeProfile(Long userId, ProfileSchema updatedProfile) {
        ProfileSchema existingProfile = profileSchemeRepo.findById(userId)
                .orElseThrow(() -> new ValidationException("Profile not found"));

        // Preserve authentication data
        UserAuthSchema existingAuth = existingProfile.getUserAuthSchema();

        // Update all profile fields except auth data
        updateProfileFields(existingProfile, updatedProfile);
        existingProfile.setUserAuthSchema(existingAuth);

        // Handle skills if present
        if (updatedProfile.getSkills() != null && !updatedProfile.getSkills().isEmpty()) {
            List<SkillSchema> savedSkills = new ArrayList<>();
            for (SkillSchema skill : updatedProfile.getSkills()) {
                skill.setProfile(existingProfile);
                savedSkills.add(skill);
            }
            existingProfile.setSkills(savedSkills);
        }

        return profileSchemeRepo.save(existingProfile);
    }

    public ProfileSchema updateProfile(Long userId, ProfileSchema updatedProfile) {
        ProfileSchema existingProfile = profileSchemeRepo.findById(userId)
                .orElseThrow(() -> new ValidationException("Profile not found"));

        // Update profile fields but preserve the authentication data
        UserAuthSchema existingAuth = existingProfile.getUserAuthSchema();
        updateProfileFields(existingProfile, updatedProfile);
        existingProfile.setUserAuthSchema(existingAuth);

        return profileSchemeRepo.save(existingProfile);
    }

    public List<ProfileSchema> getProfiles() {
        return profileSchemeRepo.findAll();
    }

    public ProfileSchema getUser(long id) {
        return profileSchemeRepo.findById(id).orElse(null);
    }

    public List<JobFormSchema> getJobsMatchingSkill(long userId) {
        ProfileSchema profile = getUser(userId);
        if (profile.getSkills() == null || profile.getSkills().isEmpty()) {
            throw new ValidationException("Profile has no skills defined");
        }
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

    private void updateProfileFields(ProfileSchema existing, ProfileSchema updated) {
        existing.setName(updated.getName());
        existing.setLanguages(updated.getLanguages());
        existing.setTitle(updated.getTitle());
        existing.setAge(updated.getAge());
        existing.setCurrentSalary(updated.getCurrentSalary());
        existing.setExpectedSalary(updated.getExpectedSalary());
        existing.setCoverLetter(updated.getCoverLetter());
        existing.setAdditionalNotes(updated.getAdditionalNotes());
        existing.setResumeUrl(updated.getResumeUrl());
        existing.setIsAvailableForRelocation(updated.getIsAvailableForRelocation());
        existing.setNoticePeriodInDays(updated.getNoticePeriodInDays());
        existing.setDescription(updated.getDescription());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setCountry(updated.getCountry());
        existing.setCity(updated.getCity());
        existing.setDisabilitySupport(updated.getDisabilitySupport());
    }
}
