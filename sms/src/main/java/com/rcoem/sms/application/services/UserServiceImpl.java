package com.rcoem.sms.application.services;

import com.rcoem.sms.application.dto.StudentDetails;
import com.rcoem.sms.application.dto.TeacherDetails;
import com.rcoem.sms.application.dto.UserDetails;
import com.rcoem.sms.application.mapper.StudentMapper;
import com.rcoem.sms.application.mapper.UserMapper;
import com.rcoem.sms.domain.entities.StudentInfo;
import com.rcoem.sms.domain.entities.UserInfo;
import com.rcoem.sms.domain.repositories.StudentRepository;
import com.rcoem.sms.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordService passwordService;

    @Autowired
    JwtService jwtService;


    @Override
    public UserDetails registerUser(UserDetails userDetails) {
        // Validate email format
        if (userDetails.getEmail() == null || !userDetails.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check for duplicate email
        if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Check for duplicate mobile
        if (userDetails.getMobileNumber() != null && !userDetails.getMobileNumber().trim().isEmpty()) {
            if (userRepository.findByMobileNumber(userDetails.getMobileNumber()).isPresent()) {
                throw new IllegalArgumentException("Mobile number already exists");
            }
        }

        String uid = "USER" + UUID.randomUUID();
        userDetails.setId(uid);
        
        // Set default type to "student" if not provided
        if(userDetails.getType() == null || userDetails.getType().trim().isEmpty()) {
            userDetails.setType("student");
        }
        
        // Hash password before saving
        if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
            String hashedPassword = passwordService.hashPassword(userDetails.getPassword());
            userDetails.setPassword(hashedPassword);
        }
        
        UserDetails userDetails1= userMapper.toDto(userRepository.save(userMapper.toEntity(userDetails)));
        if(userDetails.getType().equalsIgnoreCase("student")) {
            studentService.createStudent(StudentDetails.builder()
                    .id(userDetails.getId())
                    .name(userDetails.getName())
                    .email(userDetails.getEmail())
                    .gender(userDetails.getGender())
                    .mobileNumber(userDetails.getMobileNumber())
                    .dateOfBirth(userDetails.getDateOfBirth())
                    .department(userDetails.getDepartment())
                    .points(0)
                    .build());
        }
        else if(userDetails.getType().equalsIgnoreCase("teacher")){
            teacherService.createTeacher(TeacherDetails.builder()
                .id(userDetails.getId())
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .gender(userDetails.getGender())
                .mobileNumber(userDetails.getMobileNumber())
                .dateOfBirth(userDetails.getDateOfBirth())
                .department(userDetails.getDepartment())
                .build());
        }
        else if(userDetails.getType().equalsIgnoreCase("admin")){
            adminService.createAdmin(com.rcoem.sms.application.dto.AdminDetails.builder()
                .id(userDetails.getId())
                .name(userDetails.getName())
                .email(userDetails.getEmail())
                .mobileNumber(userDetails.getMobileNumber())
                .department(userDetails.getDepartment())
                .gender(userDetails.getGender())
                .dateOfBirth(userDetails.getDateOfBirth())
                .build());
        }
        userDetails1.setPassword(null);
        userDetails1.setToken(jwtService.generateToken(userDetails1.getId(), userDetails1.getEmail(), userDetails1.getType()));
        return userDetails1;
    }

    @Override
    public UserDetails signInUser(String email,String password) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        UserInfo userDetails=userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User Not found"));
        if(nonNull(userDetails)){
            if(passwordService.matches(password, userDetails.getPassword())){
                UserDetails userDto = userMapper.toDto(userDetails);
                userDto.setPassword(null);
                userDto.setToken(jwtService.generateToken(userDetails.getId(), userDetails.getEmail(), userDetails.getType()));
                return userDto;
            }
            throw new RuntimeException("invalid credentials");
        }
        return null;
    }

    @Override
    public UserDetails getUserDetails(UserDetails userDetails) {
        return userRepository.findById(userDetails.getId())
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public void updateUserType(UserDetails userDetails) {
        // Implementation if needed
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean mobileExists(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber).isPresent();
    }

    public UserDetails updateUser(UserDetails userDetails) {
        UserInfo existingUser = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields if provided
        if (userDetails.getName() != null) {
            existingUser.setName(userDetails.getName());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(existingUser.getEmail())) {
            // Check if new email already exists
            if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
            existingUser.setEmail(userDetails.getEmail());
        }
        if (userDetails.getMobileNumber() != null && !userDetails.getMobileNumber().equals(existingUser.getMobileNumber())) {
            // Check if new mobile already exists
            if (userRepository.findByMobileNumber(userDetails.getMobileNumber()).isPresent()) {
                throw new IllegalArgumentException("Mobile number already exists");
            }
            existingUser.setMobileNumber(userDetails.getMobileNumber());
        }
        if (userDetails.getDepartment() != null) {
            existingUser.setDepartment(userDetails.getDepartment());
        }

        UserInfo savedUser = userRepository.save(existingUser);
        UserDetails updatedDto = userMapper.toDto(savedUser);
        updatedDto.setPassword(null);
        return updatedDto;
    }
}
