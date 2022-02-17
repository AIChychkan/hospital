package com.example.hospital.services;

import com.example.hospital.model.Patient;
import com.example.hospital.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This is a service layer.
 */

@Service
@Component
@Transactional
@Slf4j //I'll be able to print smth to console to see what's happening.
//@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    //Constructor
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public void createPatient(Patient patient) {
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

    // I already have such a method in PatientRepository from JPARepository.
    public Patient getPatientByEmail(){

        return null;
    }

    // I don't know if this method will be useful to implement.
    public Patient getPatientByID(){

        return null;
    }

}
