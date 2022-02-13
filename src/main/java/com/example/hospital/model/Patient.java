package com.example.hospital.model;

import lombok.*;
import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Here are all data and methods of Patient.
 */

@Setter
@Getter
@Entity(name = "Patient")
@Table(name = "patient",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "patient_email_unique",
                        columnNames = "email")})
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @SequenceGenerator(
            name = "patient_sequence",
            sequenceName = "patient_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "patient_sequence"
    )

    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "age", nullable = false)
    private Integer age;

    public Patient(String firstName,
                   String lastName,
                   String email,
                   Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
