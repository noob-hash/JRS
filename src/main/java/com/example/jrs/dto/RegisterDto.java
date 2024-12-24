package com.example.jrs.dto;

import com.example.jrs.entity.EmployerSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.enums.UserRole;

import lombok.Data;

/**
 *
 * @author lenovo
 */
@Data
public class RegisterDto {
  private String username;
  private String password;
  private UserRole role;
  private ProfileSchema profileData; // Used for CANDIDATE registration
  private EmployerSchema employerData; // Used for EMPLOYER registration
}
