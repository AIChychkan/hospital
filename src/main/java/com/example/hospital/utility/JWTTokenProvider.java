package com.example.hospital.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.hospital.constants.SecurityConstants;
import com.example.hospital.model.UserSecurityMethods;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTTokenProvider {
    @Value("@{jwt.secureString}")
    private String secureString; // properties or jml file will access this random string to encode/decode the token;

    //method - generator of token
    public String JWTTokenGenerate(UserSecurityMethods userPrincipal) { // UserPrincipal - Spring-related user
        String[] userCredentials = getUserCredentials(userPrincipal);

        return JWT.create()
                .withIssuer(SecurityConstants.GET_ARRAYS_COMPANY) // token provider e.g. company
                .withAudience(SecurityConstants.GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date()) // when token was issued
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim(SecurityConstants.AUTHORITIES, userCredentials)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .sign(Algorithm.HMAC256(secureString.getBytes()));
    }

    private String[] getUserCredentials(UserSecurityMethods user) { // in video here is getAuthorities() method
        List<String> authorities = new ArrayList<>();

        // getting all the authorities
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }

        return authorities.toArray(new String[0]);
    }




}
