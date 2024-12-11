package com.example.jrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jrs.dto.LoginDto;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.repo.ProfileSchemeRepo;

@Service
public class AuthService {
    @Autowired
    private ProfileSchemeRepo profileRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ProfileSchema login(LoginDto loginDto) {
        ProfileSchema profile = profileRepo.findByUserAuthSchema_Username(loginDto.getUsername());
        if (passwordEncoder.matches(loginDto.getPassword(), profile.getUserAuthSchema().getPassword())) {
            return profile;
        }
        return null;
    }

    public ProfileSchema register(ProfileSchema userData) {
        if (profileRepo.findByEmail(userData.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use");
        }
        String hashedPassword = passwordEncoder.encode(userData.getUserAuthSchema().getPassword());
        userData.getUserAuthSchema().setPassword(hashedPassword);
        return profileRepo.save(userData);
    }

}
