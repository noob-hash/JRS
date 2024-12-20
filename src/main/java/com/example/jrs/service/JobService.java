package com.example.jrs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    public List<JobFormSchema> getJobs() {
        return jobRepository.findAll();
    }

    public JobFormSchema getJobById(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public List<JobFormSchema> findJobs(List<Map<String, String>> filterParam) {
        Specification<JobFormSchema> spec = Specification.where(null);

        for (Map<String, String> filter : filterParam) {
            for (Map.Entry<String, String> entry : filter.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                spec = spec
                        .and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(key), "%" + value + "%"));
            }
        }

        return jobRepository.findAll(spec);
    }
}
