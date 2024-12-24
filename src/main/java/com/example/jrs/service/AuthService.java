package com.example.jrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jrs.dto.LoginDto;
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

        switch (userAuth.getRole()) {
            case CANDIDATE:
                return profileRepo.findByUserAuthSchema_Username(loginDto.getUsername());
            case EMPLOYER:
                return employerRepo.findByUserAuthSchema_Username(loginDto.getUsername());
            case ADMIN:
                return userAuth; // For admin, we might just need the auth details
            default:
                throw new IllegalStateException("Invalid user role");
        }
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
