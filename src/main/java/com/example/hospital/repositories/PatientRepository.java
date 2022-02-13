package com.example.hospital.repositories;

import com.example.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface responsible for connection to DB.
 */

@Repository
@Transactional(readOnly = true)
public interface PatientRepository extends JpaRepository<Patient, Long> { //getting all the necessary methods from JpaRepository.
    Optional<Patient> findByEmail(String email);
}
