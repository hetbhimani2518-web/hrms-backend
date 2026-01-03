package com.example.hrms.dto.hr;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrUpdateRequest {

    private String fullName;
    private String phone;
    private String department;
    private String designation;
}
