package com.astrotalk.hospital.dto;

import lombok.Data;

@Data
public class SignUp {
    private String email;
    private String name;
    private String profession;
    private String phone;
    private String password;
    private String speciality;
}
