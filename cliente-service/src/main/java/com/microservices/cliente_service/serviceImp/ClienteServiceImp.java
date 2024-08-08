package com.microservices.cliente_service.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.cliente_service.dao.ClienteDao;
import com.microservices.cliente_service.dao.PersonaDao;
import com.microservices.cliente_service.dto.ClienteDto;
import com.microservices.cliente_service.dto.ClienteDto.ClienteDtoBuilder;
import com.microservices.cliente_service.dto.ClienteEditDto;
import com.microservices.cliente_service.model.entities.Cliente;
import com.microservices.cliente_service.model.entities.Persona;
import com.microservices.cliente_service.service.ClienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImp implements ClienteService{

	@Autowired
	ClienteDao clienteDao;
	
	@Autowired
	PersonaDao personaDao;
	
	@Override
	public void save(Cliente cliente) throws Exception {
		try {
			clienteDao.save(cliente);	
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}

	@Override
	public void updateCliente(Long clienteId, ClienteEditDto clienteDto) throws Exception {
		Cliente cliente = getCliente(clienteId);
		cliente.setContrasenia(clienteDto.getContrasena());
		cliente.setEstado(true);
		clienteDao.save(cliente);

		Persona persona = cliente.getPersona();
		persona.setNombre(clienteDto.getNombre().trim());
		persona.setGenero(clienteDto.getGenero().trim());
		persona.setEdad(Integer.parseInt(clienteDto.getEdad()));
		persona.setDireccion(clienteDto.getDireccion().trim());
		persona.setTelefono(clienteDto.getTelefono().trim());
		personaDao.save(persona);
	}

	@Override
	public List<ClienteDto> listClientes() throws Exception {
		var clientes = clienteDao.findAll();
		return clientes.stream().map(this::mapToClienteDto).toList();
	}

	@Override
	public ClienteDto getClienteDto(Long clienteId) throws Exception {
		Cliente cliente = getCliente(clienteId); 
		return ClienteDto.builder()
	            .clienteId(cliente.getClienteId()+"")
	            .nombre(cliente.getPersona().getNombre())
	            .genero(cliente.getPersona().getGenero())
	            .identificacion(cliente.getPersona().getIdentificacion())
	            .direccion(cliente.getPersona().getDireccion())
	            .telefono(cliente.getPersona().getTelefono())
	            .build(); 
	}
	
	public Cliente getCliente(Long clienteId) throws Exception {
		return clienteDao.getReferenceById(clienteId);
	}

	@Override
	public Cliente saveDTO(ClienteDto dto) throws Exception {
		try {
			
			Persona persona = new Persona();
			persona.setNombre(dto.getNombre().trim());
			persona.setGenero(dto.getGenero().trim());
			persona.setEdad(Integer.parseInt(dto.getEdad()));
			persona.setIdentificacion(dto.getIdentificacion().trim());
			persona.setDireccion(dto.getDireccion().trim());
			persona.setTelefono(dto.getTelefono().trim());
			personaDao.save(persona);

			Cliente cliente = new Cliente();
			cliente.setContrasenia(dto.getContrasena().trim());
			cliente.setEstado(true);
			cliente.setPersona(persona);
			clienteDao.save(cliente);
	        
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
		return null;
	}

	private ClienteDto mapToClienteDto(Cliente cliente) {
		return ClienteDto.builder()
				.clienteId(cliente.getClienteId() + "")
				.nombre(cliente.getPersona().getNombre())
				.genero(cliente.getPersona().getGenero())
				.edad(cliente.getPersona().getEdad() + "")
				.identificacion(cliente.getPersona().getIdentificacion())
				.direccion(cliente.getPersona().getDireccion())
				.telefono(cliente.getPersona().getTelefono())
				.contrasena(cliente.getContrasenia())
				.estado(cliente.getEstado() ? "true": "false")
				.build();
	}

	@Override
	public void deleteCliente(Long clienteId) throws Exception {
		try {
			Cliente cliente = getCliente(clienteId);
			clienteDao.delete(cliente);

			Persona persona = cliente.getPersona();
			personaDao.delete(persona);
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}

	@Override
	public boolean isCliente(Long clienteId) {
		try {
			if(getCliente(clienteId) != null) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
