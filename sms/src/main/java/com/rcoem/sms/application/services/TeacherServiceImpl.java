package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.TeacherDetails;
import com.rcoem.sms.domain.entities.TeacherInfo;
import com.rcoem.sms.domain.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public TeacherDetails createTeacher(TeacherDetails teacherDetails) {
        if (teacherDetails.getId() == null || teacherDetails.getId().trim().isEmpty()) {
            // keep id generation logic similar to users if needed
            teacherDetails.setId("TEA" + java.util.UUID.randomUUID());
        }
        TeacherInfo t = TeacherInfo.builder()
                .id(teacherDetails.getId())
                .name(teacherDetails.getName())
                .email(teacherDetails.getEmail())
                .mobileNumber(teacherDetails.getMobileNumber())
                .department(teacherDetails.getDepartment())
                .gender(teacherDetails.getGender())
                .dateOfBirth(teacherDetails.getDateOfBirth())
                .build();
        TeacherInfo saved = teacherRepository.save(t);
        teacherDetails.setId(saved.getId());
        return teacherDetails;
    }
}
