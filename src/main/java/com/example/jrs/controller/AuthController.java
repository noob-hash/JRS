package com.example.jrs.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.dto.LoginDto;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/jrs")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid ProfileSchema profileSchema) {
        return new ResponseEntity<>(authService.register(profileSchema), HttpStatus.OK);
    }

}
