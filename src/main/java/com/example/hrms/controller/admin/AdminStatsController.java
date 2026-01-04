package com.example.hrms.controller.admin;

import com.example.hrms.entity.HrStatus;
import com.example.hrms.repository.HrProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatsController {

    private final HrProfileRepository hrProfileRepository;

    @GetMapping
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("activeHr", hrProfileRepository.countByStatus(HrStatus.ACTIVE));

        // TEMP placeholders (we’ll replace later)
        stats.put("totalEmployees", 128L);
        stats.put("departments", 6L);
        stats.put("pendingLeaves", 14L);

        return stats;
    }
}
