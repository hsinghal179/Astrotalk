package com.astrotalk.hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Patients {
    @Id
    private String email;
    private String name;
    private String phone;
    private String age;
    private String room;
    private String doctor;
    private String expenses;
    private Timestamp admitTimestamp;
    private String admitStatus;
}