package com.example.jrs.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
public class JobFormSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Positive(message = "Salary must be a positive number")
    private Integer minimumSalary;

    @Positive(message = "Salary must be a positive number")
    private Integer maximumSalary;
    @NotBlank(message = "This field is required")

    private String description;
    @NotBlank(message = "This field is required")

    private String jobType;
    private String location;

    @NotBlank(message = "This field is required")
    private String experience;

    @NotBlank(message = "This field is required")
    private String jobTags;

    @NotBlank(message = "This field is required")
    private String category;

    @NotBlank(message = "This field is required")
    private String jobTitle;
    
    @Email(message = "This field must folow email format")
    private String email;

    @OneToMany(mappedBy = "jobForm", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SkillSchema> skills;
    
    private String requirement;

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
