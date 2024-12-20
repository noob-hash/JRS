package com.example.jrs.config;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.entity.SkillSchema;
import com.example.jrs.entity.UserAuthSchema;
import com.example.jrs.repo.JobSchemaRepo;
import com.example.jrs.repo.ProfileSchemeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Configuration
public class DataInitilizer {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner populateData(ProfileSchemeRepo profileSchemaRepository, JobSchemaRepo jobSchemaRepo) {
        return args -> {
            UserAuthSchema userAuthSchema = new UserAuthSchema();
            userAuthSchema.setUsername("john_doe");
            userAuthSchema.setPassword(passwordEncoder.encode("Password"));

            SkillSchema skill1 = new SkillSchema();
            skill1.setSkillName("Java");

            SkillSchema skill2 = new SkillSchema();
            skill2.setSkillName("Spring Boot");

            ProfileSchema profile = new ProfileSchema();
            profile.setName("John Doe");
            profile.setLanguages("English, Nepali");
            profile.setTitle("Software Developer");
            profile.setAge(30);
            profile.setCurrentSalary(60000);
            profile.setExpectedSalary(80000);
            profile.setDescription("Experienced Java developer with expertise in Spring Boot.");
            profile.setEmail("john.doe@example.com");
            profile.setPhone("9819292929");
            profile.setCountry("Nepal");
            profile.setCity("KTM");
            profile.setUserAuthSchema(userAuthSchema);

            skill1.setProfile(profile);
            skill2.setProfile(profile);
            profile.setSkills(List.of(skill1, skill2));

            profileSchemaRepository.save(profile);

            JobFormSchema job1 = new JobFormSchema(
                    null,
                    50000,
                    70000,
                    "Full-stack Developer position",
                    "Full-time",
                    "KTM",
                    "3+ years",
                    "Java, Angular",
                    "IT",
                    "Software Developer",
                    "example1@example.com",
                    null,
                    "RARRARRA");

            JobFormSchema job2 = new JobFormSchema(
                    null,
                    40000,
                    60000,
                    "Frontend Developer position",
                    "Part-time",
                    "KTM",
                    "2+ years",
                    "React, CSS",
                    "IT",
                    "Frontend Developer",
                    "example2@example.com",
                    null,
                    "RARRARRA");

            // job1.setSkills(List.of(skill1));SkillSchema skill1 = new SkillSchema();
            SkillSchema skill3 = new SkillSchema();
            skill3.setSkillName("Java");
            skill3.setJobForm(job1);

            SkillSchema skill4 = new SkillSchema();
            skill4.setSkillName("Spring Boot");
            skill4.setJobForm(job1);


            SkillSchema skill5 = new SkillSchema();
            skill5.setSkillName("Spring Boot");
            skill5.setJobForm(job2);


            job1.setSkills(List.of(skill3, skill4));
            job2.setSkills(List.of(skill5));

            // System.out.println(job1.toString());


            // Save all jobs with cascading skills
            jobSchemaRepo.saveAll(List.of(job1, job2));
        };
    }
}
