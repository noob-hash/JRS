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
                ProfileSchema profile = profileRepo.findByUserAuthSchema_Username(loginDto.getUsername());
                loginResponse.setProfile(profile); // Include the profile data if needed
                break;
            case EMPLOYER:
                EmployerSchema employer = employerRepo.findByUserAuthSchema_Username(loginDto.getUsername());
                loginResponse.setEmployer(employer); // Include the employer data if needed
                break;
            case ADMIN:
                loginResponse.setAdminData(userAuth); // Admin might only need auth data
                break;
            default:
                throw new IllegalStateException("Invalid user role");
        }

        return loginResponse;
    }

    public Object register(RegisterDto registerDto) {
        if (userAuthRepo.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        UserAuthSchema userAuth = new UserAuthSchema();
        userAuth.setUsername(registerDto.getUsername());
        userAuth.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userAuth.setRole(registerDto.getRole());

        switch (registerDto.getRole()) {
            case CANDIDATE:
                ProfileSchema profile = registerDto.getProfileData();
                profile.setUserAuthSchema(userAuth);
                return profileRepo.save(profile);
            case EMPLOYER:
                EmployerSchema employer = registerDto.getEmployerData();
                employer.setUserAuthSchema(userAuth);
                return employerRepo.save(employer);
            case ADMIN:
                // Admin creation might need special handling
                return userAuthRepo.save(userAuth);
            default:
                throw new IllegalStateException("Invalid user role");
        }
    }

}
