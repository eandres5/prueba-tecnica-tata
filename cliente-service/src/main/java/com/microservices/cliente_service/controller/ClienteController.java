package com.microservices.cliente_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.cliente_service.dto.ClienteDto;
import com.microservices.cliente_service.dto.ClienteEditDto;
import com.microservices.cliente_service.dto.ClienteResponseDto;
import com.microservices.cliente_service.dto.MensajeDto;
import com.microservices.cliente_service.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	public static final String MENSAJE = "mensaje";
	
	@GetMapping(value = "/isCliente/{clienteId}",  produces = "application/json")
	public boolean getIsCliente(@PathVariable Long clienteId) {
		boolean cliente =clienteService.isCliente(clienteId); 
		return cliente;
	}
	
	@GetMapping(value = "/{clienteId}", produces = "application/json")
	public ClienteDto getCliente(@PathVariable Long clienteId) throws Exception{
		try {
			ClienteDto cliente = clienteService.getClienteDto(clienteId); 
			return cliente;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@GetMapping
	public List<ClienteDto> getAllClientes() throws Exception{
		Map<String, Object> responseMap = new HashMap<>();
		ClienteResponseDto clienteReponseDto;
		try {
			return this.clienteService.listClientes();
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return (List<ClienteDto>) new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "save", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> saveCliente(@RequestBody ClienteDto clienteDto) {
		Map<String, Object> responseMap = new HashMap<>();
		ClienteResponseDto clienteReponseDto;
		try {
			clienteService.saveDTO(clienteDto);
			clienteReponseDto = new ClienteResponseDto(true, "Exito");
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ClienteResponseDto>(clienteReponseDto, HttpStatus.OK);
	}

	@PutMapping(value = "edit/{clienteId}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> editarCliente(@PathVariable("clienteId") Long clienteId,
			@RequestBody ClienteEditDto clienteEditDto) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();
		MensajeDto mensajeDto = null;
		try {
			clienteService.updateCliente(clienteId, clienteEditDto);
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MensajeDto>(mensajeDto, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "delete/{clienteId}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> borrarCliente(@PathVariable("idCliente") Long idCliente) {
		Map<String, Object> responseMap = new HashMap<>();
		MensajeDto mensajeDto;

		try {
			clienteService.deleteCliente(idCliente);
			mensajeDto = new MensajeDto(true, "Registro eliminado");
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MensajeDto>(mensajeDto, HttpStatus.OK);
	}	
}