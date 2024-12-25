package com.example.jrs.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.jrs.entity.JobApplication;
import com.example.jrs.enums.ApplicationStatus;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    
    List<JobApplication> findByCandidate_UserId(Long candidateId);
    
    List<JobApplication> findByJob_JobId(Long jobId);
    
    List<JobApplication> findByApplicationDateBetween(Date start, Date end);
    
    Long countByJob_EmployerId(Long employerId);
    
    Long countByJob_EmployerIdAndStatus(Long employerId, ApplicationStatus status);
    
    @Query("SELECT COUNT(DISTINCT a.candidate.userId) FROM JobApplication a WHERE a.job.employerId = :employerId")
    Long countUniqueCandidatesByEmployer(@Param("employerId") Long employerId);
}