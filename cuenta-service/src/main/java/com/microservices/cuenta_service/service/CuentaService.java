package com.microservices.cuenta_service.service;

import java.util.List;

import com.microservices.cuenta_service.dto.CuentaDto;

public interface CuentaService {

	void updateCliente(Long clienteId, CuentaDto cuentaDto) throws Exception;

	List<CuentaDto> listCuentas() throws Exception;

	CuentaDto getCuentaDto(Long cuentaId) throws Exception;

	void saveDTO(CuentaDto dto) throws Exception;

	void deleteCliente(Long clienteId) throws Exception;
}
