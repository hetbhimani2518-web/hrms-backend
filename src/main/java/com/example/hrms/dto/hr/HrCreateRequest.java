package com.example.hrms.dto.hr;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HrCreateRequest {

    @NotBlank
    @Email
    private String email;
    private String password;

    @NotBlank
    private String fullName;
    private String phone;
    private String department;
    private String designation;
    private LocalDate joiningDate;
}
