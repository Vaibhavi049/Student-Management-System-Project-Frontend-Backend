package com.rcoem.sms.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name = "course")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    String name;
    String code;
    String description;
    String department;
    String teacherId; // ID of the teacher/admin who created the course
    Integer maxEnrollment;
    Integer currentEnrollment;
}

