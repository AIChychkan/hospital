package com.example.hospital.repositories;

import com.example.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Interface responsible for connection to DB.
 */

@Repository
@Transactional(readOnly = true)
public interface PatientRepository extends JpaRepository<Patient, Long> { //getting all the necessary methods from JpaRepository.

/*    (Patient is Patient.java Entity)
    This is JBQL query -> SQL: SELECT * FROM patient WHERE email = ?*/
    @Query("SELECT s FROM Patient s WHERE s.email = ?1")
    Optional<Patient> findPatientByEmail(String email);

}
