package com.example.jrs.dto;

import com.example.jrs.entity.EmployerSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "This field is required")
    private String username;
    @NotBlank(message = "This field is required")
    private String password;

    private UserRole role;
    private ProfileSchema profile;
    private EmployerSchema employer;
}
