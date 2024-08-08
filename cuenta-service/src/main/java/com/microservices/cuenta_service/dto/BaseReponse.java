package com.microservices.cuenta_service.dto;

import org.springframework.context.annotation.Bean;

public record BaseReponse(String[] errorMessages) {

	public boolean hasError() {
		return errorMessages != null && errorMessages.length > 0;
	}
}
