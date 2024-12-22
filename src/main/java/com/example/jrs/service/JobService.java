package com.example.jrs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.SkillSchema;
import com.example.jrs.repo.JobSchemaRepo;
import com.example.jrs.repo.SkillsRepo;

@Service
public class JobService {
    @Autowired
    private JobSchemaRepo jobRepository;

    @Autowired
    private SkillsRepo skillSchemaRepository;

    public JobFormSchema saveJob(JobFormSchema profile) {
        return jobRepository.save(profile);
    }

    public JobFormSchema saveJobWithSkills(JobFormSchema jobForm) {
        // Save the JobFormSchema first
        JobFormSchema savedJob = jobRepository.save(jobForm);

        // Iterate through the skills and associate them with the saved job
        if (jobForm.getSkills() != null && !jobForm.getSkills().isEmpty()) {
            for (SkillSchema skill : jobForm.getSkills()) {
                skill.setJobForm(savedJob); // Link the skill to the saved job
                skillSchemaRepository.save(skill); // Save the skill
            }
        }

        return savedJob; // Return the saved job with associated skills
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
