package com.example.hospital.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import static javax.persistence.GenerationType.AUTO;

/**
 * Here are all data and methods of Patient located.
 */

@Entity //mapping this class as a table in DB
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String idToShow;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String username;
    private String password;
    private Date joinDate;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private String imageURL;
    private String diagnosis;
    private String treatment;
    private boolean isActive;
    private boolean isNotLocked;
    private String[] roles; //patient, admin, nurse, different types of doctors.
    private String[] authorities;//read, edit, delete


}
