package com.astrotalk.hospital.controller;

import com.astrotalk.hospital.dto.DischargeSummary;
import com.astrotalk.hospital.dto.Login;
import com.astrotalk.hospital.dto.SignUp;
import com.astrotalk.hospital.entity.Patients;
import com.astrotalk.hospital.service.HospitalService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @PostMapping(value = "/admin")
    @ResponseStatus(HttpStatus.OK)
    public String adminLogin(@RequestBody Login login){
        try {
            return hospitalService.adminLogin(login);
        }catch (IllegalAccessException e){
            onUnauthorizedException(e);
        }
        return null;
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public String userLogin(@RequestBody Login login){
        try {
            return hospitalService.userLogin(login);
        }catch (IllegalAccessException e){
            onUnauthorizedException(e);
        }
        return null;
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.OK)
    public String userSignup(@RequestBody SignUp signUp){
        try {
            return hospitalService.userSignUp(signUp);
        }catch (Exception e){
            Exception(e);
        }
        return null;
    }

    @GetMapping(value = "/allPatients")
    @ResponseStatus(HttpStatus.OK)
    public List<Patients> patientList(){
        try {
            return hospitalService.patientList();
        }catch (Exception e){
            Exception(e);
        }
        return null;
    }

    @PostMapping(value = "/admitPatient")
    @ResponseStatus(HttpStatus.CREATED)
    public String admitPatient(@RequestBody Patients patient){
        try {
            return hospitalService.admitPatient(patient);
        }catch (Exception e){
            Exception(e);
        }
        return null;
    }

    @PatchMapping(value = "/dischargePatient")
    @ResponseStatus(HttpStatus.OK)
    public String dischargePatient(@RequestBody DischargeSummary summary){
        try {
            return hospitalService.dischargePatient(summary);
        }catch (BadRequestException e){
            onBadRequestException(e);
        }
        return null;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void onUnauthorizedException(IllegalAccessException exception) {}

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    void Exception(Exception exception) {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void onBadRequestException(BadRequestException exception) {}
}