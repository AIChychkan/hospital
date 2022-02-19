package com.example.hospital.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component //whenever the program starts I'll get a Bean of JWTTokenProvider
public class JWTTokenProvider {

    @Value("@{jwt.secureString}") // getting secureString from .yml file
    private String secureString; // properties or jml file will access this random string to encode/decode the token;

    //method - generator of token
    public String JWTTokenGenerate(UserSecurityMethods userPrincipal) { // UserPrincipal - Spring-related user
        String[] userCredentials = getUserCredentials(userPrincipal);
        // creating json web token
        return JWT.create()
                .withIssuer(SecurityConstants.GET_ARRAYS_ISSUER) // token provider e.g. company
                .withAudience(SecurityConstants.GET_ARRAYS_ADMINISTRATION)
                .withIssuedAt(new Date()) // when token was issued
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim(SecurityConstants.AUTHORITIES, userCredentials)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                .sign(Algorithm.HMAC256(secureString.getBytes()));
    }

    //getting authentication and telling Spring to process the request
    public Authentication getAuthentication(
            String username,
            List<GrantedAuthority> grantedAuthorityList,
            HttpServletRequest httpServletRequest
    ){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        grantedAuthorityList);

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        return usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String username, String token){
        JWTVerifier jwtVerifier = getJWTVerifier();
        return StringUtils.isNoneEmpty(username) && !isTokenExpired(jwtVerifier, token);
    } // ->
    private boolean isTokenExpired(JWTVerifier jwtVerifier, String token) {
        Date expirationDate = jwtVerifier.verify(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token){
        return stream(getCredentialsFromToken(token))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    } // ->
    private String[] getCredentialsFromToken(String token) {
        return getJWTVerifier()
                .verify(token)
                .getClaim(SecurityConstants.AUTHORITIES)
                .asArray(String.class);
    } // ->
    private JWTVerifier getJWTVerifier() {
        return JWT.require(Algorithm
                .HMAC256(secureString))
                .withIssuer(SecurityConstants.GET_ARRAYS_ISSUER)
                .build();
    }

    public String getSubject(String token){
        return getJWTVerifier().verify(token).getSubject();
    }

    private String[] getUserCredentials(UserSecurityMethods user) {
        List<String> authorities = new ArrayList<>();
        // getting all the authorities
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities
                .toArray(new String[0]);
    }
}
