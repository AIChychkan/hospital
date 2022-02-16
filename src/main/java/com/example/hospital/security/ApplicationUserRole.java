package com.example.hospital.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.hospital.security.ApplicationUserPermission.*;

@AllArgsConstructor
@Getter
public enum ApplicationUserRole {
    PATIENT(Sets.newHashSet(PATIENT_DATA_READ, MEDICAL_REPORT_READ)),
    ADMIN(Sets.newHashSet(PATIENT_REGISTER, PATIENT_CHECKOUT, PATIENT_DATA_READ, PATIENT_DATA_WRITE, MEDICAL_REPORT_READ)),
    DOCTOR(Sets.newHashSet(PATIENT_DATA_READ, MEDICAL_REPORT_READ, MEDICAL_REPORT_WRITE, PATIENT_HEAL_MEDICAMENT, PATIENT_HEAL_ALL)),
    NURSE(Sets.newHashSet(PATIENT_DATA_READ, MEDICAL_REPORT_READ, PATIENT_HEAL_MEDICAMENT));

    private final Set<ApplicationUserPermission> applicationUserPermissionSet;
    //Getter

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> appUserPermissions = this.getApplicationUserPermissionSet().stream()
                .map(applicationUserPermission -> new SimpleGrantedAuthority(applicationUserPermission.getPermission()))
                .collect(Collectors.toSet());

        appUserPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return appUserPermissions;
    }
}
