package com.example.jrs.dto;

import com.example.jrs.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
  @NotBlank(message = "This field is required")
  private String email;

  private UserRole role; // CANDIDATE, EMPLOYER, etc.

  @NotBlank(message = "This field is required")
  private String password;

}
