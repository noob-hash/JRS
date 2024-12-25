/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.jrs.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author lenovo
 */

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerSchema {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long employerId;

  @NotBlank(message = "Company name is required")
  private String companyName;

  @NotBlank(message = "This field is required")
  private String industry;

  @Email(message = "Please provide a valid email")
  @Column(unique = true)
  private String email;

  @NotBlank(message = "This field is required")
  private String phone;

  @NotBlank(message = "This field is required")
  private String address;

  private String companyDescription;

  @OneToOne(cascade = CascadeType.ALL)
  @Valid
  @JsonIgnore
  private UserAuthSchema userAuthSchema;

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
