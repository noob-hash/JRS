package com.example.jrs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.service.JobService;

@RestController
@RequestMapping("jrs/jobs")
public class JobController {
    @Autowired
    JobService jobService;

    @GetMapping("getAll")
    public List<JobFormSchema> getAllJobs() {
        return jobService.getJobs();
    }

    @GetMapping("getById")
    public JobFormSchema getJobById(@RequestParam Long id) {
        return jobService.getJobById(id);
    }

    @PostMapping("saveJob")
    public JobFormSchema registerJob(@RequestBody JobFormSchema JobFormSchema) {
        return jobService.saveJob(JobFormSchema);
    }

    @PostMapping("/save")
    public JobFormSchema saveJob(@RequestBody JobFormSchema JobFormSchema) {
        return jobService.saveJobWithSkills(JobFormSchema);
        // return ResponseEntity.ok(savedJob);
    }

    @GetMapping("findJob")
    public List<JobFormSchema> findJob(@RequestParam Map<String, String> filterParams) {
        return jobService.findJobs(List.of(filterParams));
    }
}
