package com.example.hospital.enumeration;

public enum Status {
    PATIENT_IN("PATIENT_IN"),
    PATIENT_OUT("PATIENT_OUT");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
