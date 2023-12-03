package com.astrotalk.hospital.service;

import com.astrotalk.hospital.dto.DischargeSummary;
import com.astrotalk.hospital.dto.Login;
import com.astrotalk.hospital.dto.SignUp;
import com.astrotalk.hospital.entity.Patients;
import com.astrotalk.hospital.entity.Users;
import com.astrotalk.hospital.repository.PatientsRepository;
import com.astrotalk.hospital.repository.UsersRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HospitalService {

    @Autowired
    PatientsRepository patientsRepository;
    @Autowired
    UsersRepository usersRepository;

    public String adminLogin(Login login) throws IllegalAccessException{
        Optional<Users> admin = usersRepository.findById(login.getEmail());
        if(admin.isPresent()){
            Users adminUser = admin.get();
            boolean isAdmin = adminUser.getPassword().equals(login.getPassword()) &&
                    adminUser.getProfession().equals("admin");
            if(!isAdmin){
                throw new IllegalAccessException();
            }
            String refreshToken = UUID.randomUUID().toString();
            adminUser.setRefreshToken(refreshToken);
            adminUser.setLastLogin(Timestamp.from(Instant.now()));
            usersRepository.save(adminUser);
            return refreshToken;
        }
        throw new IllegalAccessException();

    }

    public String userLogin(Login login) throws IllegalAccessException{
        Optional<Users> userOpt = usersRepository.findById(login.getEmail());
        if(userOpt.isPresent()){
            Users user = userOpt.get();
            boolean isAdmin = user.getPassword().equals(login.getPassword());
            if(!isAdmin){
                throw new IllegalAccessException();
            }
            String refreshToken = UUID.randomUUID().toString();
            user.setRefreshToken(refreshToken);
            user.setLastLogin(Timestamp.from(Instant.now()));
            usersRepository.save(user);
            return "Login Successful!\nThe new token is "+refreshToken+".\nIt will be valid for 30 minutes.";
        }
        throw new IllegalAccessException();
    }

    public String userSignUp(SignUp signUp) throws Exception {
        Optional<Users> userOpt = usersRepository.findById(signUp.getEmail());
        if(!userOpt.isPresent()){
            Users user = new Users();
            String refreshToken = UUID.randomUUID().toString();
            user.setRefreshToken(refreshToken);
            user.setLastLogin(Timestamp.from(Instant.now()));
            user.setEmail(signUp.getEmail());
            user.setName(signUp.getName());
            user.setProfession(signUp.getProfession());
            user.setPhone(signUp.getPhone());
            user.setPassword(signUp.getPassword());
            user.setSpeciality(signUp.getSpeciality());
            usersRepository.save(user);
            return "SignUp Successful!\nThe new token is "+refreshToken+".\nIt will be valid for 30 minutes.";
        }
        throw new Exception();
    }

    public List<Patients> patientList() {
        return patientsRepository.findByAdmitStatus("Admitted");
    }

    public String admitPatient(Patients patient) {
        patient.setAdmitTimestamp(Timestamp.from(Instant.now()));
        patient.setAdmitStatus("Admitted");
        patientsRepository.save(patient);
        return "Patient Admitted!";
    }

    public String dischargePatient(DischargeSummary summary) throws BadRequestException {
        if(summary.getPendingBills()==0){
            Optional<Patients> optionalPatient = patientsRepository.findByRoom(summary.getRoom());
            if(!optionalPatient.isPresent() || !optionalPatient.get().getAdmitStatus().equals("Admitted"))
                throw new BadRequestException("Room is vacant already");
            Patients patient = optionalPatient.get();
            patient.setAdmitStatus("Discharged");
            patientsRepository.save(patient);
            return "Patient discharged! Please take care :)";
        }
        throw new BadRequestException("Please pay the pending bill before discharge: "+summary.getPendingBills());
    }
}