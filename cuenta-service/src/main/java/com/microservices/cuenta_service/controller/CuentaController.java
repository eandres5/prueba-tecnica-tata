package com.microservices.cuenta_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.cliente_service.dto.ClienteDto;
import com.microservices.cliente_service.dto.ClienteResponseDto;
import com.microservices.cuenta_service.dto.CuentaDto;
import com.microservices.cuenta_service.dto.CuentaResponseDto;
import com.microservices.cuenta_service.service.CuentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;
	
	public static final String MENSAJE = "mensaje";
	
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
	public ResponseEntity<?> saveCuenta(@RequestBody CuentaDto cuentaDto) {
		Map<String, Object> responseMap = new HashMap<>();
		CuentaResponseDto cuentaResponseDto;
		try {
			cuentaService.saveDTO(cuentaDto);
			cuentaResponseDto = new CuentaResponseDto(true, "Exito");
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CuentaResponseDto>(cuentaResponseDto, HttpStatus.OK);
	}

}
