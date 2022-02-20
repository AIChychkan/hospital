package com.example.hospital.controllers;


import com.example.hospital.entities.Patient;
import com.example.hospital.repos.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class PatientController {
/*  injected to PatientController by constructor*/
    private PatientRepo repository;

/*  http approach*/
/*    @GetMapping("/patients")
    List<Patient> getAll(){
        return repository.findAll();
    }*/

/*  REST approach*/
    @GetMapping("/patients")
    CollectionModel<EntityModel<Patient>> getAll() {

        List<EntityModel<Patient>> patients = repository.findAll().stream()
                .map(patient -> EntityModel.of(patient,
                        linkTo(methodOn(PatientController.class)
                                .getPatientById(patient.getId()))
                                .withSelfRel(),
                        linkTo(methodOn(PatientController.class)
                                .getAll()).withRel("patients")))
                .collect(Collectors.toList());

        return CollectionModel.of(patients, linkTo(methodOn(PatientController.class)
                .getAll())
                .withSelfRel());
    }

/*  http approach*/
/*    @GetMapping("/patients/{id}")
    EntityModel<Patient> getPatientById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(NullPointerException::new);
    }*/

/*  The return type of the method has changed from
    Employee to EntityModel<Employee>. EntityModel<T>
    is a generic container from Spring HATEOAS that
    includes not only the data but a collection of
    links.*/
    @GetMapping("/patients/{id}")
    EntityModel<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(NullPointerException::new);
/*   Adding links to make app RESTfull, using methods fromWebMvcLinkBuilder */
        return EntityModel.of(patient,
                linkTo(methodOn(PatientController.class)
                        .getPatientById(id))
                        .withSelfRel(),
                linkTo(methodOn(PatientController.class)
                        .getAll())
                        .withRel("patients"));
    }

    @PostMapping("/patients")
    Patient newPatient(@RequestBody Patient newPatient){
        return repository
                .save(newPatient);
    }

/*  + @PutMapping replacePatient
    + @DeleteMapping deletePatient*/
}
