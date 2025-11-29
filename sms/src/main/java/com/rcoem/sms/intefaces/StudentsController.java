package com.rcoem.sms.intefaces;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    StudentService studentService;

    @PostMapping
    public ResponseEntity addStudent(@RequestBody StudentDetails studentDetails) {
        StudentDetails insertedStudentDetails=studentService.createStudent(studentDetails);
        return ResponseEntity.created(URI.create("/students/"+insertedStudentDetails.getId())).build();
    }
    @CrossOrigin(origins = "*")
    @GetMapping
    public List<StudentDetails> getStudents() {
        return studentService.getAllStudents();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDetails> getStudentById( @PathVariable String id) {
        StudentDetails studentDetails= studentService.getStudentById(id);
        if(studentDetails==null){
          return  ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(studentDetails);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}/points")
    public ResponseEntity<Object> updateStudentPoints(@PathVariable String id, @RequestBody PointsUpdateRequest request) {
        try {
            StudentDetails student = studentService.getStudentById(id);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            student.setPoints((student.getPoints() != null ? student.getPoints() : 0) + request.getPoints());
            StudentDetails updated = studentService.updateStudentById(student);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage()));
        }
    }

    static class PointsUpdateRequest {
        private Integer points;
        public Integer getPoints() {
            return points;
        }
        public void setPoints(Integer points) {
            this.points = points;
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
