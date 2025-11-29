package com.rcoem.sms.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseDetails {
    String id;
    String name;
    String code;
    String description;
    String department;
    String teacherId;
    Integer maxEnrollment;
    Integer currentEnrollment;
}

