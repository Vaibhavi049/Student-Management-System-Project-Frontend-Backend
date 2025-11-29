package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.EnrollmentDetails;
import com.rcoem.sms.domain.entities.Course;
import com.rcoem.sms.domain.entities.Enrollment;
import com.rcoem.sms.domain.repositories.CourseRepository;
import com.rcoem.sms.domain.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public EnrollmentDetails enrollStudent(String studentId, String courseId) {
        // Check if already enrolled
        if (enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }

        // Check course capacity
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (course.getCurrentEnrollment() >= course.getMaxEnrollment()) {
            throw new IllegalArgumentException("Course is full");
        }

        Enrollment enrollment = Enrollment.builder()
                .studentId(studentId)
                .courseId(courseId)
                .enrollmentDate(LocalDate.now().toString())
                .status("enrolled")
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Update course enrollment count
        course.setCurrentEnrollment(course.getCurrentEnrollment() + 1);
        courseRepository.save(course);

        return toDto(savedEnrollment);
    }

    @Override
    public List<EnrollmentDetails> getStudentEnrollments(String studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDetails> getCourseEnrollments(String courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDetails> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void dropEnrollment(String enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        // Update course enrollment count
        Course course = courseRepository.findById(enrollment.getCourseId())
                .orElse(null);
        if (course != null) {
            course.setCurrentEnrollment(Math.max(0, course.getCurrentEnrollment() - 1));
            courseRepository.save(course);
        }

        enrollmentRepository.deleteById(enrollmentId);
    }

    private EnrollmentDetails toDto(Enrollment enrollment) {
        return EnrollmentDetails.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .courseId(enrollment.getCourseId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .status(enrollment.getStatus())
                .build();
    }
}

