package com.example.hrms.service;

import com.example.hrms.dto.hr.HrCreateRequest;
import com.example.hrms.dto.hr.HrResponse;
import com.example.hrms.dto.hr.HrUpdateRequest;
import com.example.hrms.entity.*;
import com.example.hrms.repository.HrProfileRepository;
import com.example.hrms.repository.RoleRepository;
import com.example.hrms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HrManagementService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HrProfileRepository hrProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public HrResponse createHr(HrCreateRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role hrRole = roleRepository.findByRoleName("ROLE_HR").orElseThrow(() -> new RuntimeException("ROLE_HR not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.getRoles().add(hrRole);

        user =  userRepository.save(user);

        HrProfile profile = new HrProfile();
        profile.setUser(user);
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setDepartment(request.getDepartment());
        profile.setDesignation(request.getDesignation());
        profile.setJoiningDate(request.getJoiningDate());
        profile.setEmployeeCode(generateEmployeeCode());
        profile.setStatus(HrStatus.ACTIVE);
        profile.setCreatedAt(Instant.now());
        profile.setUpdatedAt(Instant.now());

        hrProfileRepository.save(profile);

        return mapToResponse(profile);
    }

    @Transactional(readOnly = true)
    public List<HrResponse> getAllHrs() {
        return hrProfileRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HrResponse getHrById(Long id) {
        HrProfile profile = hrProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HR not found"));
        return mapToResponse(profile);
    }

    public HrResponse updateHr(Long id, HrUpdateRequest request) {

        HrProfile profile = hrProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setDepartment(request.getDepartment());
        profile.setDesignation(request.getDesignation());
        profile.setUpdatedAt(Instant.now());

        return mapToResponse(profile);
    }

    public void disableHr(Long id) {

        HrProfile profile = hrProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        profile.setStatus(HrStatus.DISABLED);
        profile.getUser().setEnabled(false);
        profile.setUpdatedAt(Instant.now());
    }

    public void deleteHr(Long id) {

        HrProfile profile = hrProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("HR not found"));

        hrProfileRepository.delete(profile);
        userRepository.delete(profile.getUser());
    }

    private String generateEmployeeCode() {
        return "HR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private HrResponse mapToResponse(HrProfile profile) {
        HrResponse response = new HrResponse();
        response.setId(profile.getId());
        response.setEmail(profile.getUser().getEmail());
        response.setEnabled(profile.getUser().isEnabled());
        response.setFullName(profile.getFullName());
        response.setPhone(profile.getPhone());
        response.setDepartment(profile.getDepartment());
        response.setDesignation(profile.getDesignation());
        response.setEmployeeCode(profile.getEmployeeCode());
        response.setJoiningDate(profile.getJoiningDate());
        response.setStatus(profile.getStatus().name());
        return response;
    }
}
