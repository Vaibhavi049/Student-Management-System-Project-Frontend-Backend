package com.rcoem.sms.application.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDetails {
    String id;
    String name;
    String email;
    String mobileNumber;
    String department;
    String gender;
    String dateOfBirth;
}
