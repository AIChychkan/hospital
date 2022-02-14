package com.example.hospital.controllers;

import com.example.hospital.model.Patient;
import com.example.hospital.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller interacts with user API.
 */

@RestController
@RequestMapping(path = "patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //Get List of Patients
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_DOCTOR')")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    //Get Patient by ID
    @GetMapping(path = "{patientId}")
    public Patient getPatient(@PathVariable("patientId") Long patientId) {
        return patientService.getPatients()
                .stream()
                .filter(patient -> patientId.equals(patient.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student with ID: " + patientId + " does not exist"));
    }

    @PostMapping// Takes email: is exists - throw an exception, if not - we add
    public void addNewPatient(@RequestBody Patient patient) { //Take requestBody and MAP it to Patient.
        patientService.addNewPatient(patient);
    }

    @DeleteMapping(path = "{patientId}")
    public void deletePatient(@PathVariable("patientId") Long patientId) {
        patientService.deletePatient(patientId);
    }

    //Update Patient Information
    @PutMapping("{patientId}")
    public void updatePatient(@PathVariable("patientId") Long patientId,
                              @RequestParam(required = false) String firstName, //"required = false" means not required for pathing as a parameter
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email) {
        patientService.updatePatient(patientId, firstName, lastName, email);
    }
}
