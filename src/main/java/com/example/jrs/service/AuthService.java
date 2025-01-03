package com.example.jrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jrs.dto.LoginDto;
import com.example.jrs.dto.LoginResponseDto;
import com.example.jrs.dto.RegisterDto;
import com.example.jrs.entity.EmployerSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.entity.UserAuthSchema;
import com.example.jrs.repo.EmployerSchemaRepo;
import com.example.jrs.repo.ProfileSchemeRepo;
import com.example.jrs.repo.UserAuthRepo;

import jakarta.validation.ValidationException;

@Service
public class AuthService {
    @Autowired
    private ProfileSchemeRepo profileRepo;

    @Autowired
    private EmployerSchemaRepo employerRepo;

    @Autowired
    private UserAuthRepo userAuthRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    // public ProfileSchema login(LoginDto loginDto) {
    // ProfileSchema profile =
    // profileRepo.findByUserAuthSchema_Username(loginDto.getUsername());
    // if (passwordEncoder.matches(loginDto.getPassword(),
    // profile.getUserAuthSchema().getPassword())) {
    // return profile;
    // }
    // return null;
    // }

    // public ProfileSchema register(ProfileSchema userData) {
    // if (profileRepo.findByEmail(userData.getEmail()) != null) {
    // throw new IllegalArgumentException("Email is already in use");
    // }
    // String hashedPassword =
    // passwordEncoder.encode(userData.getUserAuthSchema().getPassword());
    // userData.getUserAuthSchema().setPassword(hashedPassword);
    // return profileRepo.save(userData);
    // }

    public Object login(LoginDto loginDto) {
        UserAuthSchema userAuth = userAuthRepo.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), userAuth.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // Create response object and set user role and data
        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setUsername(userAuth.getUsername());
        loginResponse.setRole(userAuth.getRole().toString()); // Convert role to string

        switch (userAuth.getRole()) {
            case CANDIDATE:
                ProfileSchema profile = userAuth.getProfile();
                if (profile == null) {
                    throw new IllegalStateException("Profile not found for candidate user");
                }
                loginResponse.setProfile(profile);
                break;
            case EMPLOYER:
                EmployerSchema employer = userAuth.getEmployer();
                if (employer == null) {
                    throw new IllegalStateException("Employer data not found for employer user");
                }
                loginResponse.setEmployer(employer);
                break;
            case ADMIN:
                loginResponse.setAdminData(userAuth);
                break;
            default:
                throw new IllegalStateException("Invalid user role");
        }

        return loginResponse;
    }

    public Object register(RegisterDto registerDto) {

        // Check if username already exists
        if (userAuthRepo.existsByUsername(registerDto.getUsername())) {
            throw new ValidationException("Username is already taken");
        }

        // Create and save UserAuthSchema first
        UserAuthSchema userAuth = createUserAuth(registerDto);
        userAuth = userAuthRepo.save(userAuth);

        // Handle role-specific registration
        switch (registerDto.getRole()) {
            case CANDIDATE:
                return registerCandidate(registerDto, userAuth);
            case EMPLOYER:
                return "Not registed yes";
            // return registerEmployer(registerDto, userAuth);
            case ADMIN:
                return userAuth;
            default:
                throw new IllegalStateException("Invalid user role");
        }
    }

    private UserAuthSchema createUserAuth(RegisterDto registerDto) {
        UserAuthSchema userAuth = new UserAuthSchema();
        userAuth.setUsername(registerDto.getUsername());
        userAuth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userAuth.setRole(registerDto.getRole());
        userAuth.setActive(true);
        return userAuth;
    }

    private ProfileSchema registerCandidate(RegisterDto registerDto, UserAuthSchema userAuth) {
        ProfileSchema profile = registerDto.getProfileData();
        if (profile == null) {
            throw new ValidationException("Profile data is required for candidate registration");
        }

        // Validate profile data
        validateProfileData(profile);
        userAuth.setProfile(profile);
        profile.setUserAuthSchema(userAuth);
        return profileRepo.save(profile);
    }

    private void validateProfileData(ProfileSchema profile) {
        if (profile.getName() == null || profile.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        if (profile.getEmail() == null || profile.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        // Add more profile validations as needed
    }

}
