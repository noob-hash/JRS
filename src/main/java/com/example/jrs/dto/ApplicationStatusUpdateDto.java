/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.jrs.dto;

import com.example.jrs.enums.ApplicationStatus;

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
public class ApplicationStatusUpdateDto {
  @NotNull(message = "Application ID is required")
  private Long applicationId;

  @NotNull(message = "Status is required")
  private ApplicationStatus status;

  private String employerFeedback;
  private Integer interviewRound;
}