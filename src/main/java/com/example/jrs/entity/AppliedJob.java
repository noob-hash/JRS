package com.example.jrs.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class AppliedJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @ManyToOne
    // @JoinColumn(name = "user_profile_id", nullable = false)
    private ProfileSchema userProfile;

    @ManyToOne
    // @JoinColumn(name = "applied_job_id", nullable = false)
    private JobFormSchema job;

    private LocalDate appliedDate;

    private String status;
}
