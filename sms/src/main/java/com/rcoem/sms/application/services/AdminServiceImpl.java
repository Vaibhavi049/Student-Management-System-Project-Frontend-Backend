package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.AdminDetails;
import com.rcoem.sms.domain.entities.AdminInfo;
import com.rcoem.sms.domain.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminRepository adminRepository;

    @Override
    public AdminDetails createAdmin(AdminDetails adminDetails) {
        if (adminDetails.getId() == null || adminDetails.getId().trim().isEmpty()) {
            adminDetails.setId("ADM" + java.util.UUID.randomUUID());
        }
        AdminInfo a = AdminInfo.builder()
                .id(adminDetails.getId())
                .name(adminDetails.getName())
                .email(adminDetails.getEmail())
                .mobileNumber(adminDetails.getMobileNumber())
                .department(adminDetails.getDepartment())
                .gender(adminDetails.getGender())
                .dateOfBirth(adminDetails.getDateOfBirth())
                .build();
        AdminInfo saved = adminRepository.save(a);
        adminDetails.setId(saved.getId());
        return adminDetails;
    }
}
