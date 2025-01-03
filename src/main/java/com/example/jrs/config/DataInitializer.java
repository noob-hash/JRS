// package com.example.jrs.config;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.example.jrs.entity.Company;
// import com.example.jrs.entity.JobApplication;
// import com.example.jrs.entity.JobFormSchema;
// import com.example.jrs.entity.ProfileSchema;
// import com.example.jrs.entity.SkillSchema;
// import com.example.jrs.entity.UserAuthSchema;
// import com.example.jrs.enums.ApplicationStatus;
// import com.example.jrs.enums.UserRole;
// import com.example.jrs.repo.CompanyRepository;
// import com.example.jrs.repo.JobApplicationRepository;
// import com.example.jrs.repo.JobSchemaRepo;
// import com.example.jrs.repo.ProfileSchemeRepo;

// @Configuration
// public class DataInitializer {

// @Autowired
// PasswordEncoder passwordEncoder;

// @Bean
// public CommandLineRunner populateData(
// ProfileSchemeRepo profileSchemaRepository,
// JobSchemaRepo jobSchemaRepo,
// JobApplicationRepository jobApplicationRepo,
// CompanyRepository companyRepo) {
// return args -> {
// // Create Company
// Company company = new Company();
// company.setCompanyName("Tech Solutions Inc.");
// company.setIndustry("Technology");
// company.setCompanySize("50-200");
// company.setLocation("Silicon Valley");
// company.setWebsite("https://techsolutions.com");
// company.setEmail("contact@techsolutions.com");
// company.setPhone("1234567890");
// company.setDescription("Leading provider of innovative technology
// solutions.");

// company = companyRepo.save(company);

// // Create Candidate Profile
// UserAuthSchema candidateAuth = new UserAuthSchema();
// candidateAuth.setUsername("john_doe");
// candidateAuth.setPassword(passwordEncoder.encode("Password"));
// candidateAuth.setRole(UserRole.CANDIDATE);
// candidateAuth.setActive(true);

// SkillSchema skill1 = new SkillSchema();
// skill1.setSkillName("Java");

// SkillSchema skill2 = new SkillSchema();
// skill2.setSkillName("Spring Boot");

// ProfileSchema candidateProfile = new ProfileSchema();
// candidateProfile.setName("John Doe");
// candidateProfile.setLanguages("English, Nepali");
// candidateProfile.setTitle("Software Developer");
// candidateProfile.setAge(30);
// candidateProfile.setCurrentSalary(60000);
// candidateProfile.setExpectedSalary(80000);
// candidateProfile.setDescription("Experienced Java developer with expertise in
// Spring Boot.");
// candidateProfile.setEmail("john.doe@example.com");
// candidateProfile.setPhone("9819292929");
// candidateProfile.setCountry("Nepal");
// candidateProfile.setCity("KTM");
// candidateProfile.setUserAuthSchema(candidateAuth);
// candidateProfile.setDisabilitySupport(true);

// skill1.setProfile(candidateProfile);
// skill2.setProfile(candidateProfile);
// candidateProfile.setSkills(List.of(skill1, skill2));

// candidateProfile = profileSchemaRepository.save(candidateProfile);

// // Create Job Postings
// JobFormSchema job1 = new JobFormSchema(
// null,
// company.getId(),
// 50000,
// 70000,
// "Senior Full-stack Developer",
// "Full-time",
// "KTM",
// "3+ years",
// "Java, Angular",
// "IT",
// "Software Developer",
// "hiring@techsolutions.com",
// null,
// "We are looking for an experienced full-stack developer.",
// null,
// null);

// JobFormSchema job2 = new JobFormSchema(
// null,
// company.getId(),
// 40000,
// 60000,
// "Frontend Developer",
// "Part-time",
// "KTM",
// "2+ years",
// "React, CSS",
// "IT",
// "Frontend Developer",
// "hiring@techsolutions.com",
// null,
// "Seeking a talented frontend developer.",
// null,
// null);

// // Add skills to jobs
// SkillSchema jobSkill1 = new SkillSchema();
// jobSkill1.setSkillName("Java");
// jobSkill1.setJobForm(job1);

// SkillSchema jobSkill2 = new SkillSchema();
// jobSkill2.setSkillName("Spring Boot");
// jobSkill2.setJobForm(job1);

// SkillSchema jobSkill3 = new SkillSchema();
// jobSkill3.setSkillName("React");
// jobSkill3.setJobForm(job2);

// job1.setSkills(List.of(jobSkill1, jobSkill2));
// job2.setSkills(List.of(jobSkill3));

// job1 = jobSchemaRepo.save(job1);
// job2 = jobSchemaRepo.save(job2);

// // Create Job Applications
// JobApplication application1 = new JobApplication();
// application1.setCandidate(candidateProfile);
// application1.setJob(job1);
// application1.setStatus(ApplicationStatus.PENDING);
// application1.setCoverLetter("I am excited to apply for this position...");
// application1.setExpectedSalary(65000);
// application1.setIsAvailableForRelocation(true);
// application1.setNoticePeriodInDays(30);
// application1.setAdditionalNotes("Available for immediate interview");

// JobApplication application2 = new JobApplication();
// application2.setCandidate(candidateProfile);
// application2.setJob(job2);
// application2.setStatus(ApplicationStatus.PENDING);
// application2.setCoverLetter("I would like to apply for the frontend
// position...");
// application2.setExpectedSalary(55000);
// application2.setIsAvailableForRelocation(false);
// application2.setNoticePeriodInDays(45);
// application2.setAdditionalNotes("Prefer remote work arrangement");

// jobApplicationRepo.saveAll(List.of(application1, application2));
// };
// }
// }