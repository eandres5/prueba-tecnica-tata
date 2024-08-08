package com.microservices.cuenta_service.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public GlobalException(String message) {
        super(message);
    }
    
}
