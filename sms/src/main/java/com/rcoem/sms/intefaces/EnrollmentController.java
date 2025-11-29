package com.rcoem.sms.intefaces;

import com.rcoem.sms.application.dto.EnrollmentDetails;
import com.rcoem.sms.application.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> enrollStudent(@RequestBody EnrollmentRequest request) {
        try {
            EnrollmentDetails enrollment = enrollmentService.enrollStudent(request.getStudentId(), request.getCourseId());
            return ResponseEntity.ok(enrollment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<EnrollmentDetails>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDetails>> getStudentEnrollments(@PathVariable String studentId) {
        return ResponseEntity.ok(enrollmentService.getStudentEnrollments(studentId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentDetails>> getCourseEnrollments(@PathVariable String courseId) {
        return ResponseEntity.ok(enrollmentService.getCourseEnrollments(courseId));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<?> dropEnrollment(@PathVariable String enrollmentId) {
        try {
            enrollmentService.dropEnrollment(enrollmentId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
        }
    }

    static class EnrollmentRequest {
        private String studentId;
        private String courseId;

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }
    }

    static class ErrorResponse {
        private String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}

