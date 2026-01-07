package com.example.hrms.config;

import com.example.hrms.entity.Role;
import com.example.hrms.entity.User;
import com.example.hrms.repository.RoleRepository;
import com.example.hrms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> roles = List.of(
                    "ROLE_ADMIN",
                    "ROLE_HR",
                    "ROLE_MANAGER",
                    "ROLE_EMPLOYEE"
            );

            for(String roleName : roles) {
                roleRepository.findByRoleName(roleName)
                        .orElseGet(() -> {
                            Role role =  new Role();
                            role.setRoleName(roleName);
                            return roleRepository.save(role);
                        });
            }
        };
    }

    @Bean
    CommandLineRunner initAdmin(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if(userRepository.findByEmail("admin@hrms.com").isEmpty()){
                Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN").orElseThrow();

                User admin = new  User();
                admin.setEmail("admin@hrms.com");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.getRoles().add(adminRole);

                userRepository.save(admin);

            }
        };
    }
}
