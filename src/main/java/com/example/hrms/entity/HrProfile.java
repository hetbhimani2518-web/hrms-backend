package com.example.hrms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "hr_profiles")
@Getter
@Setter
public class HrProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id" , nullable = false , unique = true)
    private User user;

    private String fullName;

    @Column(length = 15)
    private String phone;

    private String department;

    private String designation;

    @Column(unique = true)
    private String employeeCode;

    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private HrStatus status;

    private Instant createdAt;

    private Instant updatedAt;

}
