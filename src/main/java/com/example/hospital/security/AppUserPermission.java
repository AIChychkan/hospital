package com.example.hospital.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppUserPermission {

    //Registration
    PATIENT_REGISTER("patient:register"),
    PATIENT_CHECKOUT("patient:checkout"),

    //Data
    PATIENT_DATA_READ("patient:read"),
    PATIENT_DATA_WRITE("patient:write"), //change data

    //Report
    MEDICAL_REPORT_READ("medicalReport:read"),
    MEDICAL_REPORT_WRITE("medicalReport:write"), //change data

    //Healing
    PATIENT_HEAL_MEDICAMENT("patient:healMedicament"),
    PATIENT_HEAL_ALL("patient:healAll");

    private final String permission;
}
