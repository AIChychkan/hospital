package com.example.hospital.model;

import com.example.hospital.enumeration.Status;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Here are all data and methods of Patient.
 */


@Entity(name = "Patient")
@Table(name = "patient",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "patient_email_unique",
                        columnNames = "email")})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Patient {
    @Id
    @SequenceGenerator(
            name = "patient_sequence",
            sequenceName = "patient_sequence",
            allocationSize = 1 //на сколько увеличивать
    )
    @GeneratedValue(
            strategy = SEQUENCE,//it can be AUTO etc.
            generator = "patient_sequence"
    )

    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT", unique = true) //email cannot be NOT uniquem maybe I'll change Primary Key from ID to Email.
    private String email;

    //added new data below:
    @Column(name = "birthDate", nullable = false, columnDefinition = "TEXT")
    private String birthDate;

    @Column(name = "imageURL", nullable = false, columnDefinition = "TEXT")
    private String imageURL;

    @Column(name = "diagnosis", nullable = false, columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "treatment", nullable = false, columnDefinition = "TEXT")
    private String treatment;

    @Column(name = "status", nullable = false, columnDefinition = "TEXT")
    private Status status; //the patient is being treated or not

    @Column(name = "doctor", nullable = false, columnDefinition = "TEXT")
    private String doctor;

    public Patient(
            String firstName,
            String lastName,
            String email,
            Integer age,
            String imageURL,
            String diagnosis,
            String treatment,
            Status status,
            String doctor
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.imageURL = imageURL;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.status = status;
        this.doctor = doctor;
    }
}
