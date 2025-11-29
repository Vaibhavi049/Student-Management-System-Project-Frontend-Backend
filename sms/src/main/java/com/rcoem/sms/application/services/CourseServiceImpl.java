package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.CourseDetails;
import com.rcoem.sms.domain.entities.Course;
import com.rcoem.sms.domain.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseDetails createCourse(CourseDetails courseDetails) {
        // Check if course code already exists
        if (courseRepository.findByCode(courseDetails.getCode()).isPresent()) {
            throw new IllegalArgumentException("Course code already exists");
        }
        
        Course course = Course.builder()
                .name(courseDetails.getName())
                .code(courseDetails.getCode())
                .description(courseDetails.getDescription())
                .department(courseDetails.getDepartment())
                .teacherId(courseDetails.getTeacherId())
                .maxEnrollment(courseDetails.getMaxEnrollment())
                .currentEnrollment(0)
                .build();
        
        Course savedCourse = courseRepository.save(course);
        return toDto(savedCourse);
    }

    @Override
    public List<CourseDetails> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDetails> getCoursesByDepartment(String department) {
        return courseRepository.findByDepartment(department).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDetails> getCoursesByTeacher(String teacherId) {
        return courseRepository.findByTeacherId(teacherId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDetails getCourseById(String id) {
        return courseRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public CourseDetails updateCourse(CourseDetails courseDetails) {
        if (courseDetails == null || courseDetails.getId() == null) {
            throw new IllegalArgumentException("Course ID is required");
        }
        
        Course course = courseRepository.findById(courseDetails.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (courseDetails.getName() != null && !courseDetails.getName().isEmpty()) {
            course.setName(courseDetails.getName());
        }
        if (courseDetails.getDescription() != null && !courseDetails.getDescription().isEmpty()) {
            course.setDescription(courseDetails.getDescription());
        }
        if (courseDetails.getMaxEnrollment() != null && courseDetails.getMaxEnrollment() > 0) {
            course.setMaxEnrollment(courseDetails.getMaxEnrollment());
        }
        
        Course updatedCourse = courseRepository.save(course);
        return toDto(updatedCourse);
    }

    @Override
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    private CourseDetails toDto(Course course) {
        return CourseDetails.builder()
                .id(course.getId())
                .name(course.getName())
                .code(course.getCode())
                .description(course.getDescription())
                .department(course.getDepartment())
                .teacherId(course.getTeacherId())
                .maxEnrollment(course.getMaxEnrollment())
                .currentEnrollment(course.getCurrentEnrollment())
                .build();
    }
}

