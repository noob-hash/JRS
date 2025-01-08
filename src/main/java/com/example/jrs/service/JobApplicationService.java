package com.example.jrs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jrs.dto.JobApplicationDto;
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

  public JobApplication applyForJob(Long candidateId, Long jobId) {

    ProfileSchema candidate = profileRepository.findById(candidateId)
        .orElseThrow(() -> new IllegalArgumentException("Candidate not found with ID: " + candidateId));

    JobFormSchema job = jobRepository.findById(jobId)
        .orElseThrow(() -> new IllegalArgumentException("Job not found with ID: " + jobId));

    // Check if candidate has already applied
    // Optional<JobApplication> existingApplication =
    // applicationRepository.findByCandidateUserIdAndJobJobId(candidateId, jobId);

    // if (existingApplication.isPresent()) {
    // throw new IllegalStateException("Candidate has already applied for this
    // job");
    // }

    JobApplication application = new JobApplication();
    application.setCandidate(candidate);
    application.setJob(job);
    application.setStatus(ApplicationStatus.PENDING);
    // application.setApplicationDate(new Date());

    return applicationRepository.save(application);
  }

  /**
   * Retrieves all applications for a specific candidate.
   *
   * @param candidateId The ID of the candidate
   * @return List of JobApplicationDto objects
   */
  public List<JobApplicationDto> getApplicationsByCandidate(Long candidateId) {
    // Verify candidate exists
    // findCandidateById(candidateId);

    return applicationRepository.findByCandidate_UserId(candidateId)
        .stream()
        .map(this::convertToDto)
        .toList();
  }

  public List<JobApplication> getApplicationsByJob(Long jobId) {
    return applicationRepository.findByJob_JobId(jobId);

  }

  /**
   * Retrieves application statistics for an employer.
   *
   * @param employerId The ID of the employer
   * @return Map containing various statistics
   */
  public Map<String, Object> getEmployerApplicationStats(Long employerId) {
    return Map.of(
        "totalApplications", applicationRepository.countByJob_EmployerId(employerId),
        "uniqueCandidates", applicationRepository.countUniqueCandidatesByEmployer(employerId),
        "acceptedApplications",
        applicationRepository.countByJob_EmployerIdAndStatus(employerId, ApplicationStatus.OFFER_ACCEPTED),
        "pendingApplications",
        applicationRepository.countByJob_EmployerIdAndStatus(employerId, ApplicationStatus.PENDING));
  }

  /**
   * Retrieves applications within a specific date range.
   *
   * @param start Start date
   * @param end   End date
   * @return List of JobApplication objects
   */
  public List<JobApplication> getApplicationsForPeriod(Date start, Date end) {
    if (start.after(end)) {
      throw new IllegalArgumentException("Start date must be before end date");
    }
    return applicationRepository.findByApplicationDateBetween(start, end);
  }

  /**
   * Updates the status of a job application.
   *
   * @param applicationId The ID of the application to update
   * @param status        The new status
   * @param feedback      Optional feedback from the employer
   * @return Updated JobApplication
   */
  public JobApplication updateApplicationStatus(
      Long applicationId,
      ApplicationStatus status,
      String feedback) {
    JobApplication application = applicationRepository.findById(applicationId).orElseThrow(null);
    application.setStatus(status);
    if (feedback != null && !feedback.trim().isEmpty()) {
      application.setEmployerFeedback(feedback);
    }

    return applicationRepository.save(application);
  }

  /**
   * Convert JobApplication to JobApplicationDto.
   */
  private JobApplicationDto convertToDto(JobApplication application) {
    return JobApplicationDto.builder()
        .applicationId(application.getApplicationId())
        .candidateId(application.getCandidate().getUserId())
        .candidateName(application.getCandidate().getName())
        .jobId(application.getJob().getJobId())
        .jobTitle(application.getJob().getJobTitle())
        .status(application.getStatus().name())
        .applicationDate(application.getApplicationDate())
        .lastUpdatedDate(application.getLastUpdatedDate())
        .employerFeedback(application.getEmployerFeedback())
        .build();
  }
}
