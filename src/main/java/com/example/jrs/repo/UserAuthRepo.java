
package com.example.jrs.repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.jrs.entity.UserAuthSchema;
import com.example.jrs.enums.UserRole;

/**
 *
 * @author lenovo
 */
@Repository
public interface UserAuthRepo extends JpaRepository<UserAuthSchema, Long> {
  Optional<UserAuthSchema> findByUsername(String username);

  boolean existsByUsername(String username);

  // Role-based queries
  List<UserAuthSchema> findByRole(UserRole role);

  long countByRole(UserRole role);

  // Account status queries
  List<UserAuthSchema> findByIsActive(boolean isActive);

  long countByIsActive(boolean isActive);

  // Combined queries
  List<UserAuthSchema> findByRoleAndIsActive(UserRole role, boolean isActive);

  // Custom query to find users with pending profile completion
  // @Query("SELECT u FROM UserAuthSchema u LEFT JOIN u.profile p WHERE p IS NULL AND u.role = 'CANDIDATE'")
  // List<UserAuthSchema> findCandidatesWithoutProfiles();

  // Security related queries
  // @Query("SELECT u FROM UserAuthSchema u WHERE u.failedLoginAttempts >= :maxAttempts")
  // List<UserAuthSchema> findLockedAccounts(@Param("maxAttempts") int maxAttempts);

  // Password reset queries
  // Optional<UserAuthSchema> findByResetToken(String resetToken);

  // List<UserAuthSchema> findByResetTokenExpiryBefore(Date date);
}
