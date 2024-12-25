
package com.example.jrs.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jrs.entity.JobApplication;

@Repository
public interface ApplicationRepo extends JpaRepository<JobApplication, Long> {
  List<JobApplication> findByCandidate_UserId(Long userId);

  List<JobApplication> findByJob_EmployerId(Long employerId);

  int countByJob_JobId(Long jobId);

  int countByJob_JobIdAndStatus(Long jobId, String status);

  List<JobApplication> findByApplicationDateBetween(Date startDate, Date endDate);

  @Query(value = "SELECT COUNT(DISTINCT a.candidateId) FROM job_application a WHERE a.job_id = :employerId", nativeQuery = true)
  long countUniqueCandidatesByEmployer(@Param("employerId") Long employerId);
}