/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.jrs.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author lenovo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {
  @NotNull(message = "Job ID is required")
  private Long jobId;

  @NotNull(message = "Candidate ID is required")
  private Long candidateId;

  private Long applicationId;
  private String candidateName;
  private String jobTitle;
  private String status;
  private Date applicationDate;
  private Date lastUpdatedDate;
  private String employerFeedback;

}