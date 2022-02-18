package com.example.hospital.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HTTPResponse {
    private int httpStatusCode; //200, 201, 404, 500
    private HttpStatus httpStatus;
    private String reason; // e.g. 404 - status, NOT_FOUND - reason
    private String message; // developer's message e.g. "your request was successful"
}
