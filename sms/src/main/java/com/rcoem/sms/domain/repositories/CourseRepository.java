package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findByDepartment(String department);
    Optional<Course> findByCode(String code);
    List<Course> findByTeacherId(String teacherId);
}

