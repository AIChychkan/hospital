package com.example.hospital.entities;

import com.example.hospital.enums.Speciality;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DOCTOR")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Doctor {
    private @Id @GeneratedValue Long id;
    private String name;
    private String email;
    private String speciality;

    public Doctor(
            String name,
            String email,
            String speciality) {
        this.name = name;
        this.email = email;
        this.speciality = speciality;
    }
}
