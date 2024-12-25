package com.example.jrs.entity;

import com.example.jrs.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    @NotBlank(message = "This field is required")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "This field is required")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isActive = true;

}