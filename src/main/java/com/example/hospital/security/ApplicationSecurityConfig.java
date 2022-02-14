package com.example.hospital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.hospital.security.AppUserPermission.*;
import static com.example.hospital.security.AppUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //The order of antMatchers matter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("index", "css/*", "js/*").permitAll() //whitelisting URLs from authentication
                .antMatchers("/patient/**").hasRole(ADMIN.name())//provide access to URL only for specific users
                .antMatchers(HttpMethod.DELETE, "/management/**").hasAuthority(PATIENT_REGISTER.getPermission())
                .antMatchers(HttpMethod.POST, "/management/**").hasAuthority(PATIENT_REGISTER.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/**").hasAuthority(PATIENT_REGISTER.getPermission())
                .antMatchers(HttpMethod.GET, "/management/**").hasAnyRole(
                        ADMIN.name(), PATIENT.name(), DOCTOR.name(), NURSE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() { //how you retrieve a user from DB

        UserDetails marchUser = User.builder()
                .username("march")
                .password(passwordEncoder.encode("0000"))
//                .roles(AppUserRole.PATIENT.name())
                .authorities(PATIENT.getGrantedAuthorities())
                .build();

        UserDetails bartUser = User.builder()
                .username("bart")
                .password(passwordEncoder.encode("0000"))
//                .roles(AppUserRole.PATIENT.name())
                .authorities(PATIENT.getGrantedAuthorities())
                .build();

        UserDetails juliusUser = User.builder()
                .username("julius")
                .password(passwordEncoder.encode("0000"))
//                .roles(AppUserRole.DOCTOR.name())
                .authorities(DOCTOR.getGrantedAuthorities())
                .build();

        UserDetails nickUser = User.builder()
                .username("nick")
                .password(passwordEncoder.encode("0000"))
//                .roles(AppUserRole.NURSE.name())
                .authorities(NURSE.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("0000"))
//                .roles(AppUserRole.ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(bartUser, marchUser, juliusUser, nickUser, adminUser);
    }
}
