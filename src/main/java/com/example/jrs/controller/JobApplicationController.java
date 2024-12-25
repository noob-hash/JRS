package com.example.jrs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jrs.entity.JobApplication;
import com.example.jrs.service.JobApplicationService;

import java.util.Date;

@RestController
@RequestMapping("jrs/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping("/apply")
    public ResponseEntity<JobApplication> applyForJob(
            @RequestParam Long candidateId,
            @RequestParam Long jobId,
            @RequestParam Integer expectedSalary,
            @RequestParam(required = false) Boolean isAvailableForRelocation,
            @RequestParam Integer noticePeriodInDays,
            @RequestParam(required = false) String coverLetter) {

        JobApplication application = jobApplicationService.applyForJob(
                candidateId, jobId, expectedSalary,
                isAvailableForRelocation, noticePeriodInDays, coverLetter);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByCandidate(
            @PathVariable Long candidateId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByCandidate(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getApplicationsByJob(
            @PathVariable Long jobId) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJob(jobId));
    }

    @GetMapping("/employer/{employerId}/stats")
    public ResponseEntity<Map<String, Object>> getEmployerStats(
            @PathVariable Long employerId) {
        return ResponseEntity.ok(jobApplicationService.getEmployerApplicationStats(employerId));
    }

    @GetMapping("/period")
    public ResponseEntity<List<JobApplication>> getApplicationsForPeriod(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(jobApplicationService.getApplicationsForPeriod(startDate, endDate));
    }
}