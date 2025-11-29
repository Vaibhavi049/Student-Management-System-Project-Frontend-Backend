package com.rcoem.sms.domain.repositories;

import com.rcoem.sms.domain.entities.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherInfo, String> {
}
