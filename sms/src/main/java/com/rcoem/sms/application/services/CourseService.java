package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.CourseDetails;

import java.util.List;

public interface CourseService {
    CourseDetails createCourse(CourseDetails courseDetails);
    List<CourseDetails> getAllCourses();
    List<CourseDetails> getCoursesByDepartment(String department);
    List<CourseDetails> getCoursesByTeacher(String teacherId);
    CourseDetails getCourseById(String id);
    CourseDetails updateCourse(CourseDetails courseDetails);
    void deleteCourse(String id);
}

