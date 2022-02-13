package com.example.hospital;

import com.example.hospital.model.Patient;
import com.example.hospital.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PatientConfig {
    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            Patient march = new Patient(
                    "March",
                    "Simpson",
                    "m.simpson@gmail.com",
                    30);

            patientRepository.save(march);

            Patient bart = new Patient(
                    "Bart",
                    "Simpson",
                    "b.simpson@gmail.com",
                    10);

            patientRepository.saveAll(List.of(march, bart));
        };
    }
}
