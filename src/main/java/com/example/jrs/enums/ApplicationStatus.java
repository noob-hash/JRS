
package com.example.jrs.enums;

/**
 *
 * @author lenovo
 */
public enum ApplicationStatus {
  PENDING("Application Submitted"),
  UNDER_REVIEW("Under Review"),
  SHORTLISTED("Shortlisted"),
  INTERVIEW_SCHEDULED("Interview Scheduled"),
  INTERVIEW_COMPLETED("Interview Completed"),
  OFFER_EXTENDED("Offer Extended"),
  OFFER_ACCEPTED("Offer Accepted"),
  OFFER_DECLINED("Offer Declined"),
  REJECTED("Application Rejected"),
  WITHDRAWN("Application Withdrawn");

  private final String displayName;

  ApplicationStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}