package com.example.jrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.entity.AppliedJob;
import com.example.jrs.service.AppliedJobService;

@RestController
@RequestMapping("jrs/appliedJobs")
public class AppliedJobController {

    @Autowired
    AppliedJobService appliedJobService;

    @PostMapping("/apply")
    public ResponseEntity<AppliedJob> applyForJob(@RequestParam Long userId, @RequestParam Long jobId) {
        AppliedJob appliedJob = appliedJobService.applyForJob(userId, jobId);
        return ResponseEntity.ok(appliedJob);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppliedJob>> getApplicationsByUser(@PathVariable("userId") Long userId) {
        List<AppliedJob> applications = appliedJobService.getApplicationsByUser(userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<AppliedJob>> getApplicationsByJob(@PathVariable("jobId") Long jobId) {
        List<AppliedJob> applications = appliedJobService.getApplicationsByJob(jobId);
        return ResponseEntity.ok(applications);
    }
}
