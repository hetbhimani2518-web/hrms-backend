package com.example.hrms.dto.hr;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HrCreateRequest {

    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String department;
    private String designation;
    private LocalDate joiningDate;
}
