package com.example.jrs.dto;

import com.example.jrs.entity.EmployerSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.entity.UserAuthSchema;

public class LoginResponseDto {
  private String username;
  private String role; // User role as string (CANDIDATE, EMPLOYER, ADMIN)
  private ProfileSchema profile;
  private EmployerSchema employer;
  private UserAuthSchema adminData;

  // Getters and setters
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public ProfileSchema getProfile() {
    return profile;
  }

  public void setProfile(ProfileSchema profile) {
    this.profile = profile;
  }

  public EmployerSchema getEmployer() {
    return employer;
  }

  public void setEmployer(EmployerSchema employer) {
    this.employer = employer;
  }

  public UserAuthSchema getAdminData() {
    return adminData;
  }

  public void setAdminData(UserAuthSchema adminData) {
    this.adminData = adminData;
  }
}
