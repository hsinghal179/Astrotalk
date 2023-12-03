package com.astrotalk.hospital.repository;

import com.astrotalk.hospital.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientsRepository extends JpaRepository<Patients,String> {
    List<Patients> findByAdmitStatus(String status);
    Optional<Patients> findByRoom(String room);
}
