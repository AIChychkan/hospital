package com.example.hospital.services;

import com.example.hospital.model.Patient;
import com.example.hospital.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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

        if (patientOptional.isPresent())
            throw new IllegalStateException("email taken");

        patientRepository.save(patient);
    }

    public void deletePatient(Long patientId) {
        boolean exists = patientRepository.existsById(patientId);
        if (exists)
            patientRepository.deleteById(patientId);
        else throw new IllegalStateException("student with ID: " + patientId + " doesn't exist");
    }

    @Transactional//Entity goes into managed state, and we do not need to use queries
    //Update Patient info
    public void updatePatient(Long patientId, String firstName, String lastName, String email) {

        //checking if student exists
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalStateException("student with ID: " + patientId + " doesn't exist"));

        //here wwe can add check if arguments are not null of length = 0 and check if arguments are not the same as existing.
        patient.setFirstName(firstName);
        patient.setLastName(lastName);

        //check if email has not been already taken
        Optional<Patient> patientOptional = patientRepository.findPatientByEmail(email);
        if (patientOptional.isPresent())
            throw new IllegalStateException("student with email: " + email + " already exist");
        patient.setEmail(email);

    }
}
