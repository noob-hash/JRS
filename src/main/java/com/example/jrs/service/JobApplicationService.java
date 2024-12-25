package com.example.jrs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jrs.entity.JobApplication;
import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.enums.ApplicationStatus;
import com.example.jrs.repo.JobApplicationRepository;
import com.example.jrs.repo.JobSchemaRepo;
import com.example.jrs.repo.ProfileSchemeRepo;

/**
 *
 * @author lenovo
 */
@Service
public class JobApplicationService {
  @Autowired
  private ProfileSchemeRepo profileRepository;

  @Autowired
  private JobSchemaRepo jobRepository;

  @Autowired
  private JobApplicationRepository applicationRepository;

  public JobApplication applyForJob(Long candidateId, Long jobId, Integer expectedSalary,
      Boolean isAvailableForRelocation, Integer noticePeriodInDays, String coverLetter) {

    ProfileSchema candidate = profileRepository.findById(candidateId)
        .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID: " + candidateId));

    JobFormSchema job = jobRepository.findById(jobId)
        .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

    JobApplication application = new JobApplication();
    application.setCandidate(candidate);
    application.setJob(job);
    application.setStatus(ApplicationStatus.PENDING);
    application.setExpectedSalary(expectedSalary);
    application.setIsAvailableForRelocation(isAvailableForRelocation);
    application.setNoticePeriodInDays(noticePeriodInDays);
    application.setCoverLetter(coverLetter);
    application.setApplicationDate(new Date());

    return applicationRepository.save(application);
  }

  public List<JobApplication> getApplicationsByCandidate(Long candidateId) {
    return applicationRepository.findByCandidate_UserId(candidateId);
  }

  public List<JobApplication> getApplicationsByJob(Long jobId) {
    return applicationRepository.findByJob_JobId(jobId);
  }

  public Map<String, Object> getEmployerApplicationStats(Long employerId) {
    return Map.of(
        "totalApplications", applicationRepository.countByJob_EmployerId(employerId),
        "uniqueCandidates", applicationRepository.countUniqueCandidatesByEmployer(employerId),
        "acceptedApplications",
        applicationRepository.countByJob_EmployerIdAndStatus(employerId, ApplicationStatus.OFFER_ACCEPTED),
        "pendingApplications",
        applicationRepository.countByJob_EmployerIdAndStatus(employerId, ApplicationStatus.PENDING));
  }

  public List<JobApplication> getApplicationsForPeriod(Date start, Date end) {
    return applicationRepository.findByApplicationDateBetween(start, end);
  }
}
