package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.EnrollmentDetails;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDetails enrollStudent(String studentId, String courseId);
    List<EnrollmentDetails> getStudentEnrollments(String studentId);
    List<EnrollmentDetails> getCourseEnrollments(String courseId);
    List<EnrollmentDetails> getAllEnrollments();
    void dropEnrollment(String enrollmentId);
}

