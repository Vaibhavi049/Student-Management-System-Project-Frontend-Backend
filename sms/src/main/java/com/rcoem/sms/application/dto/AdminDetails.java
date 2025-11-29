package com.rcoem.sms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDetails {
    private String id;
    private String name;
    private String email;
    private String mobileNumber; // mobile_number
    private String department;
    private String gender;
    private String dateOfBirth; // date_of_birth
}
