package com.example.hospital.repos;

import com.example.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient, Long> {
}
