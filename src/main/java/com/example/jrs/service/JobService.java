package com.example.jrs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.repo.JobSchemaRepo;

@Service
public class JobService {
    @Autowired
    private JobSchemaRepo jobRepository;

    public JobFormSchema saveJob(JobFormSchema profile) {
        return jobRepository.save(profile);
    }

    public List<JobFormSchema> getJobs(){
        return jobRepository.findAll();
    }

    public JobFormSchema getJobById(long id){
        return jobRepository.findById(id).orElse(null);
    }
}
