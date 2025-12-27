package com.example.hrms.repository;

import com.example.hrms.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}