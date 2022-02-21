package com.example.hospital;

import com.example.hospital.entities.Doctor;
import com.example.hospital.entities.Patient;
import com.example.hospital.repos.DoctorRepo;
import com.example.hospital.repos.PatientRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.hospital.enums.Speciality.*;
import static com.example.hospital.enums.Status.COMPLETED;
import static com.example.hospital.enums.Status.IN_PROGRESS;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDB(PatientRepo patientRepo, DoctorRepo doctorRepo) {
        return args -> {
            patientRepo.save(new Patient("Bart", "bart@gmail.com", IN_PROGRESS.name()));
            patientRepo.save(new Patient("March", "march@gmail.com", IN_PROGRESS.name()));
            patientRepo.save(new Patient("Homer", "homer@gmail.com", COMPLETED.name()));
            patientRepo.findAll().forEach(patient -> log.info("Preloaded " + patient));

            doctorRepo.save(new Doctor("Julius", "julius@gmail.com", DERMATOLOGIST.name()));
            doctorRepo.save(new Doctor("Nick", "nick@gmail.com", NURSE.name()));
            doctorRepo.save(new Doctor("Luis", "lluis@gmail.com", PULMONOLOGIST.name()));
            doctorRepo.findAll().forEach(doctor -> log.info("Preloaded " + doctor));

        };
    }
}
