package com.astrotalk.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Users {
    @Id
    private String email;
    private String name;
    private String phone;
    private String password;
    private String speciality;
    private String profession;
    private Timestamp lastLogin;
    private String refreshToken;
}