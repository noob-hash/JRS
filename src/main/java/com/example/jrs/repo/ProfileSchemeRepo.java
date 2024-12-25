package com.example.jrs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.jrs.entity.JobFormSchema;
import com.example.jrs.entity.ProfileSchema;

@Repository
public interface ProfileSchemeRepo extends JpaRepository<ProfileSchema, Long> {
    ProfileSchema findByEmail(String username);

    boolean existsByEmail(String email);

    long countByUserAuthSchema_IsActive(boolean isActive);

    @Query(value = "Select * From jrs.profile_schema p join jrs.user_auth_schema u on p.user_auth_schema_auth_id = u.auth_id where u.username =:username", nativeQuery = true)
    ProfileSchema findByUserAuthSchema_Username(String username);

    @Query(value = "SELECT j.*  FROM job_form_schema j JOIN skill_schema js ON j.id = js.job_form_id JOIN skill_schema ps ON ps.profile_id = :userId WHERE js.skill_name = ps.skill_name", nativeQuery = true)
    List<JobFormSchema> getJobsMatchingSkill(Long userId);

}
