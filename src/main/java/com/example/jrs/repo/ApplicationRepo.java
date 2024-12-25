
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
  List<JobApplication> findByCandidateId(Long userId);

  List<JobApplication> findByJobEmployerId(Long employerId);

  int countByJobId(Long jobId);

  int countByJobIdAndStatus(Long jobId, String status);

  List<JobApplication> findByApplicationDateBetween(Date startDate, Date endDate);

  @Query("SELECT COUNT(DISTINCT a.candidateId) FROM JobApplication a WHERE a.job.employerId = :employerId")
  long countUniqueCandidatesByEmployer(@Param("employerId") Long employerId);
}