
package com.example.jrs.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jrs.entity.EmployerSchema;

/**
 *
 * @author lenovo
 */
@Repository
public interface EmployerSchemaRepo extends JpaRepository<EmployerSchema, Long> {
  EmployerSchema findByUserAuthSchema_Username(String username);

  EmployerSchema findByEmail(String email);

  boolean existsByEmail(String email);

  long countByUserAuthSchema_IsActive(boolean isActive);
}
