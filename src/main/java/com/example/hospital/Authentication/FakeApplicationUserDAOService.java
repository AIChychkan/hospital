package com.example.hospital.Authentication;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.hospital.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDAOService implements ApplicationUserDAO{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDAOService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().
                stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUserList = Lists.newArrayList( //redundant, I can just return the List
            new ApplicationUser(
                    "march@gmail.com", // I'll have email as a username
                    passwordEncoder.encode("0000"),
                    PATIENT.getGrantedAuthorities(), //gets Set<>
                    true,
                    true,
                    true,
                    true
            ),
            new ApplicationUser(
                    "julius@gmail.com",
                    passwordEncoder.encode("0000"),
                    DOCTOR.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            ),
            new ApplicationUser(
                    "nick@gmail.com",
                    passwordEncoder.encode("0000"),
                    NURSE.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            )
        );

        return applicationUserList;
    }


}
