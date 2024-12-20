package com.example.jrs.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jrs.entity.AppliedJob;
import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.repo.AppliedJobsRepo;
import com.example.jrs.repo.JobSchemaRepo;
import com.example.jrs.repo.ProfileSchemeRepo;

@Service
public class AppliedJobService {

    @Autowired
    ProfileSchemeRepo profileRepository;

    @Autowired
    JobSchemaRepo jobFormSchemaRepository;

    @Autowired
    AppliedJobsRepo appliedJobRepository;

    public AppliedJob applyForJob(Long userId, Long jobId) {
        ProfileSchema user = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        JobFormSchema job = jobFormSchemaRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

        AppliedJob appliedJob = new AppliedJob();
        appliedJob.setUserProfile(user);
        appliedJob.setJob(job);
        appliedJob.setAppliedDate(LocalDate.now());
        appliedJob.setStatus("Pending");

        return appliedJobRepository.save(appliedJob);
    }

    public List<AppliedJob> getApplicationsByUser(Long userId) {
        return appliedJobRepository.findByUserProfileId(userId);
    }

    public List<AppliedJob> getApplicationsByJob(Long jobId) {
        return appliedJobRepository.findByJobId(jobId);
    }
}
