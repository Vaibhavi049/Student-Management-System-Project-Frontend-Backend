package com.rcoem.sms.intefaces;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.application.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDetails courseDetails) {
        try {
            CourseDetails created = courseService.createCourse(courseDetails);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<CourseDetails>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/department/{department}")
    public ResponseEntity<List<CourseDetails>> getCoursesByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(courseService.getCoursesByDepartment(department));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<CourseDetails>> getCoursesByTeacher(@PathVariable String teacherId) {
        return ResponseEntity.ok(courseService.getCoursesByTeacher(teacherId));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDetails> getCourseById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(courseService.getCourseById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<CourseDetails> updateCourse(@PathVariable String id, @RequestBody CourseDetails courseDetails) {
        try {
            courseDetails.setId(id);
            return ResponseEntity.ok(courseService.updateCourse(courseDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable String id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage()));
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

