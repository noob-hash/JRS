package com.example.jrs.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ProfileSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Name must be at least 2 characters")
    private String name;
    @NotBlank(message = "Name must be at least 2 characters")
    private String languages;
    @NotBlank(message = "Name must be at least 2 characters")
    private String title;

    @Min(message = "Must be at least 18 years old", value = 18)
    @Max(message = "Must be at most 100 years old", value = 100)
    private Integer age;

    @Positive(message = "Salary must be a positive number")
    private Integer currentSalary;

    @Positive(message = "Salary must be a positive number")
    private Integer expectedSalary;

    @NotBlank(message = "This field is required")
    private String description;

    @Email(message = "This field must folow email format")
    private String email;

    @NotBlank(message = "This field is required")
    private String phone;

    @NotBlank(message = "This field is required")
    private String country;

    @NotBlank(message = "This field is required")
    private String city;

    // @NotBlank(message = "This field is required")
    // private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    private UserAuthSchema userAuthSchema;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SkillSchema> skills;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    @PrePersist
    private void onCreate() {
        createdDate = new Date();
        lastUpdatedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedDate = new Date();
    }
}
