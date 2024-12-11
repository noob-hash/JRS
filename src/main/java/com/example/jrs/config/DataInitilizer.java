package com.example.jrs.config;

import com.example.jrs.entity.ProfileSchema;
import com.example.jrs.entity.SkillSchema;
import com.example.jrs.entity.UserAuthSchema;
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
    public CommandLineRunner populateData(ProfileSchemeRepo profileSchemaRepository) {
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
        };
    }
}
