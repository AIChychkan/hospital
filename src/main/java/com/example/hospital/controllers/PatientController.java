package com.example.hospital.controllers;

import com.example.hospital.model.Patient;
import com.example.hospital.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @PostMapping// Take email: is exists - throw an exception, if not - we add
    public void registerNewPatient(@RequestBody Patient patient){ //Take requestBody and MAP it to Patient.
        patientService.addNewPatient(patient);
    }
}
