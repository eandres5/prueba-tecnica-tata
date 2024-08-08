package com.microservices.cuenta_service.Exception;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
	
	private Date timestamp;
    private int statusCode;
    private String message;
    private String details;

    public ErrorDetails(int statusCode, String message, String details) {
        this.timestamp = new Date();
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

}
