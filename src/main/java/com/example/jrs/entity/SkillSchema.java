package com.example.jrs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "skill_schema") // Add this
@Entity
public class SkillSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    private String skillName;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "profile_id")
    private ProfileSchema profile;

    @ManyToOne(optional = true)
    @JsonIgnore
    @JoinColumn(name = "job_form_id")
    private JobFormSchema jobForm;
}
