/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.jrs.entity;

import java.util.Date;

import com.example.jrs.enums.ApplicationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class JobApplication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long applicationId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "candidate_id", nullable = false)
  @NotNull(message = "Candidate is required")
  private ProfileSchema candidate;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "job_id", nullable = false)
  private JobFormSchema job;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ApplicationStatus status = ApplicationStatus.PENDING;

  @Column(length = 2000)
  private String coverLetter;

  @Column(length = 1000)
  private String additionalNotes;

  private String resumeUrl;

  @NotNull(message = "Expected salary is required")
  private Integer expectedSalary;

  private Boolean isAvailableForRelocation;

  @NotNull(message = "Notice period is required")
  private Integer noticePeriodInDays;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date applicationDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdatedDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date employerReviewDate;

  @Column(length = 500)
  private String employerFeedback;

  private Integer interviewRound = 0;

  @PrePersist
  protected void onCreate() {
    applicationDate = new Date();
    lastUpdatedDate = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    lastUpdatedDate = new Date();
  }
}
