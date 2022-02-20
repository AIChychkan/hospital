package com.example.hospital.repos;

import com.example.hospital.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {
}
