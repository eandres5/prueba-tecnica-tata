package com.microservices.cuenta_service.service;

import java.util.List;

import com.microservices.cuenta_service.dto.CuentaDto;
import com.microservices.cuenta_service.dto.CuentaEditDto;
import com.microservices.cuenta_service.mode.entities.Cuenta;

public interface CuentaService {

	List<CuentaDto> listCuentas() throws Exception;

	CuentaDto getCuentaDto(Long cuentaId) throws Exception;

	void saveDTO(CuentaDto dto) throws Exception;

	void deleteCuenta(Long cuentaId) throws Exception;
	
	void updateCuenta (Long cuentaId, CuentaEditDto cuentaDto) throws Exception;

	Cuenta getCuentaByNumeroCuenta(String numeroCuenta) throws Exception;
}

