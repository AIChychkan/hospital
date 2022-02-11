package com.example.hospital;

import com.example.hospital.repositories.TreatmentRepository;
import com.example.hospital.services.HospitalService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HospitalServiceTest {

    private TreatmentRepository treatmentRepository = new TreatmentRepository();
    private HospitalService hospitalService = new HospitalService(treatmentRepository);

    @Test
    void getTreatment() {
        String treatmentForEbola =  hospitalService.getTreatment("ebola");
        assertEquals(treatmentForEbola, "water;");
    }

    @Test
    void addTreatment() {
        String treatmentForEbola =  hospitalService.addTreatment("ebola", " worm pills;");
        assertEquals(treatmentForEbola, "water; worm pills;");
    }
}