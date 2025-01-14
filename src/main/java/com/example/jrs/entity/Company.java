package com.example.jrs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String companyName;

    @NotBlank(message = "Industry is required")
    private String industry;

    @NotBlank(message = "Company size is required")
    private String companySize;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Website is required")
    @Pattern(regexp = "^(https?://)?[\\w.-]+\\.[a-z]{2,}$", message = "Please enter a valid URL")
    private String website;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Please enter a valid phone number")
    private String phone;

    @NotBlank(message = "Description is required")
    @Size(min = 50, max = 500, message = "Description must be between 50 and 500 characters")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @JsonIgnore
    private UserAuthSchema userAuthSchema;

    private String fileUrl; // Stores document securely

    private String companyLogo; // Stores document securely

    private boolean isDocumentVerified; // Tracks verification status

    private String documentRemarks; // Verification remarks
}
