package com.rcoem.sms.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity(name = "user_info")
@Builder
// private String type = "USER";
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    String id;
    String name;

    String type;

    @Column(unique = true)
    String email;
    
    @Column(unique = true)
    String mobileNumber;

    String password;
    String gender;
    String dateOfBirth;
    String department;
}
