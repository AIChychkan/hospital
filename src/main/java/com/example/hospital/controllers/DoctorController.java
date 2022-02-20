package com.example.hospital.controllers;

import com.example.hospital.entities.Doctor;
import com.example.hospital.repos.DoctorRepo;
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
public class DoctorController {
    private final DoctorRepo repository;

    @GetMapping("/doctors")
    CollectionModel<EntityModel<Doctor>> getAll() {
        List<EntityModel<Doctor>> doctors = repository.findAll().stream()
                .map(doctor -> EntityModel.of(doctor,
                        linkTo(methodOn(DoctorController.class)
                                .getDoctorById(doctor.getId()))
                                .withSelfRel(),
                        linkTo(methodOn(DoctorController.class)
                                .getAll()).withRel("doctors")))
                .collect(Collectors.toList());

        return CollectionModel.of(doctors, linkTo(methodOn(DoctorController.class)
                .getAll())
                .withSelfRel());
    }

    @GetMapping("/doctors/{id}")
    EntityModel<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(NullPointerException::new);

        return EntityModel.of(doctor,
                linkTo(methodOn(DoctorController.class)
                        .getDoctorById(id))
                        .withSelfRel(),
                linkTo(methodOn(DoctorController.class)
                        .getAll())
                        .withRel("doctors"));
    }

    @PostMapping("/doctors")
    Doctor newDoctor(@RequestBody Doctor newDoctor){
        return repository
                .save(newDoctor);
    }


/*  + @PutMapping replacePatient
    + @DeleteMapping deletePatient*/
}
