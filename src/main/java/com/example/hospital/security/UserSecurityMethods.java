package com.example.hospital.security;

import com.example.hospital.model.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class UserSecurityMethods implements UserDetails {
    private Patient patient;

    public UserSecurityMethods(Patient patient) {
        this.patient = patient;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(patient.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return patient.getPassword();
    }

    @Override
    public String getUsername() {
        return patient.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return patient.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return patient.isActive();
    }
}
