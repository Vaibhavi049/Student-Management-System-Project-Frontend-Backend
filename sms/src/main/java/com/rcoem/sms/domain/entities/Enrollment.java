package com.rcoem.sms.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity(name = "enrollment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    
    String studentId;
    String courseId;
    
    @Column(name = "enrollment_date")
    String enrollmentDate;
    
    String status; // enrolled, completed, dropped
}

