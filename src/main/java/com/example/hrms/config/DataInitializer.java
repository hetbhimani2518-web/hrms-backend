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
import java.util.Set;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("HR");
        createRoleIfNotExists("MANAGER");
        createRoleIfNotExists("EMPLOYEE");

        createAdminIfNotExists();
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByRoleName(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return roleRepository.save(role);
                });
    }

    private void createAdminIfNotExists() {
        if (userRepository.findByEmail("admin@gmail.com").isPresent()) {
            return;
        }

        Role adminRole = roleRepository.findByRoleName("ADMIN")
                .orElseThrow(() -> new RuntimeException("ADMIN role missing"));

        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setEnabled(true);
        admin.setRoles(Set.of(adminRole));

        userRepository.save(admin);
    }
}