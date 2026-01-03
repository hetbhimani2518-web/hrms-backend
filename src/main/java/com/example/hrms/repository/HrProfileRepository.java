package com.example.hrms.repository;

import com.example.hrms.entity.HrProfile;
import com.example.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HrProfileRepository extends JpaRepository<HrProfile, Long> {

    Optional<HrProfile> findByUser(User user);

    Optional<HrProfile> findByEmployeeCode(String employeeCode);
}
