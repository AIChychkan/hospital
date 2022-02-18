package com.example.hospital.constants;

public class SecurityConstants {
    public final static long EXPIRATION_DATE = 432_000_000; // 5 days in milliseconds
    public final static String TOKEN_PREFIX = "Bearer "; // a person who has this token-prefix has the rights to be authenticated without real token;
    public final static String JWT_TOKEN_HEADER = "JWT-Token"; // custom header to which real token will be attached
    public final static String TOKEN_HEADER_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public final static String GET_ARRAYS_COMPANY = "Token issued by Apple"; // issuer of the token
    public final static String GET_ARRAYS_ADMINISTRATION = "Token issued to Administration"; // issued to the specific audience e.g. admins
    public static final String AUTHORITIES = "authorities"; // user authorities
    public static final String FORBIDDEN_MESSAGE = "Forbidden to access the page.Please, log in"; // a person got a token but is forbidden to access the resource
    public static final String ACCESS_DENY_MESSAGE = "You don't have permission to access this page";
    public static final String OPTION_HTTP_METHOD = "Options";
    public static final String[] PUBLIC_URLS = {
            "/patient/login",
            "/patient/register",
            "/patient/resetpassword/**",
            "/patient/image/**",
            "/index/**",
    }; // URLs to be accessed without any security
}
