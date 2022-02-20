package com.example.hospital.entities;

import com.example.hospital.enums.Status;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENT")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Patient {
    private @Id @GeneratedValue Long id;
    private String name;
    private String email;
    private String status;

    public Patient(
            String name,
            String email,
            String status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }
}
