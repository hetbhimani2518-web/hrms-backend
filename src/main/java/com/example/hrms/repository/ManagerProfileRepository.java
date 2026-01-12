package com.example.hrms.repository;

import com.example.hrms.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerProfileRepository extends JpaRepository<ManagerProfile, Long> {

    Optional<ManagerProfile> findByUser(User user);

    long countByStatus(ManagerStatus status);

    Optional<ManagerProfile> findByEmployeeCode(String employeeCode);

    Optional<ManagerProfile> findByIdAndStatus(Long id, ManagerStatus status);
}
