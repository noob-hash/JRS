/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.jrs.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jrs.repo.ApplicationRepo;

/**
 *
 * @author lenovo
 */
@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepo applicationRepo;

    public Map<String, Object> getEmployerApplicationStats(Long employerId) {
        return Map.of(
                "totalApplications", applicationRepo.findByJob_EmployerId(employerId).size(),
                "uniqueCandidates", applicationRepo.countUniqueCandidatesByEmployer(employerId),
                "acceptedApplications", applicationRepo.countByJob_JobIdAndStatus(employerId, "ACCEPTED"),
                "pendingApplications", applicationRepo.countByJob_JobIdAndStatus(employerId, "PENDING"));
    }

    // public List<AppliedJob> getCandidateApplicationHistory(Long candidateId) {
    // return applicationRepo.findByCandidateId(candidateId);
    // }

    // public List<AppliedJob> getApplicationsForPeriod(Date start, Date end) {
    // return applicationRepo.findByApplicationDateBetween(start, end);
    // }
}