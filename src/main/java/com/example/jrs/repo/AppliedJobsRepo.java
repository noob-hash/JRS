package com.example.jrs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jrs.entity.AppliedJob;

@Repository
public interface AppliedJobsRepo extends JpaRepository<AppliedJob, Long> {
    
    @Query(value = "SELECT * FROM applied_job aj WHERE aj.user_profile_id = :userId", nativeQuery = true)
    List<AppliedJob> findByUserProfileId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM applied_job aj WHERE aj.job_id = :jobId", nativeQuery = true)
    List<AppliedJob> findByJobId(@Param("jobId") Long jobId);
}
