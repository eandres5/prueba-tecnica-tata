package com.microservices.cuenta_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaEditDto {

	private String tipoCuenta;
	private String estado;
	private String saldoInicial;
}
