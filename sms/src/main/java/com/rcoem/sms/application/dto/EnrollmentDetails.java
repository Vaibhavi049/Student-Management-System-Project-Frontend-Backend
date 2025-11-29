package com.rcoem.sms.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EnrollmentDetails {
    String id;
    String studentId;
    String courseId;
    String enrollmentDate;
    String status;
}

