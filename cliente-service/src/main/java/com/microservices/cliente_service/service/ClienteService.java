package com.microservices.cliente_service.service;

import java.util.List;

import com.microservices.cliente_service.dto.ClienteDto;
import com.microservices.cliente_service.dto.ClienteDto.ClienteDtoBuilder;
import com.microservices.cliente_service.dto.ClienteEditDto;
import com.microservices.cliente_service.model.entities.Cliente;

public interface ClienteService {
	
	void save (Cliente cliente) throws Exception;
	
	void updateCliente (Long clienteId, ClienteEditDto clienteDto) throws Exception;
	
	List<ClienteDto> listClientes() throws Exception;
	
	ClienteDto getClienteDto(Long clienteId) throws Exception;
	
    Cliente saveDTO(ClienteDto dto) throws Exception;
	
	void deleteCliente (Long clienteId) throws Exception;
	
	boolean isCliente(Long clienteId);
}
