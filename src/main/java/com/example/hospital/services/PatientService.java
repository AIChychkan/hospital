package com.example.hospital.services;

import com.example.hospital.model.Patient;
import com.example.hospital.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * This is a service layer.
 */

//@Service
@Component
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    /**
     * Business logic. addNewPatient adds new patient if its email doesn't exist in DB.
     */
    public void addNewPatient(Patient patient) {
        Optional<Patient> patientOptional = patientRepository.findPatientByEmail(patient.getEmail());

        if(patientOptional.isPresent())
            throw new IllegalStateException("email taken");

        patientRepository.save(patient);
    }
}
