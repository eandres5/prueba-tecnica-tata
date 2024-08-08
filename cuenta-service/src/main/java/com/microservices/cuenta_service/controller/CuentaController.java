package com.microservices.cuenta_service.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.microservices.cuenta_service.dto.CuentaDto;
import com.microservices.cuenta_service.dto.CuentaEditDto;
import com.microservices.cuenta_service.dto.CuentaResponseDto;
import com.microservices.cuenta_service.dto.MensajeDto;
import com.microservices.cuenta_service.service.CuentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;
	
	public static final String MENSAJE = "mensaje";
	
	@GetMapping(value = "/{cuentaId}", produces = "application/json")
	public CuentaDto getCuenta(@PathVariable Long cuentaId) throws Exception{
		try {
			CuentaDto cuenta = cuentaService.getCuentaDto(cuentaId); 
			return cuenta;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@GetMapping
	public List<CuentaDto> getAllCuentas() throws Exception{
		Map<String, Object> responseMap = new HashMap<>();
		CuentaResponseDto cuentaResponseDto;
		try {
			return this.cuentaService.listCuentas();
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return (List<CuentaDto>) new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
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
	
	@PutMapping(value = "edit/{cuentaId}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> editarCuenta(@PathVariable("cuentaId") Long cuentaId,
			@RequestBody CuentaEditDto cuentaEditDto) throws Exception {
		Map<String, Object> responseMap = new HashMap<>();
		MensajeDto mensajeDto = null;
		try {
			cuentaService.updateCuenta(cuentaId, cuentaEditDto);
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MensajeDto>(mensajeDto, HttpStatus.OK);
	}

	@DeleteMapping(value = "delete/{cuentaId}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> borrarCuenta(@PathVariable("cuentaId") Long cuentaId) {
		Map<String, Object> responseMap = new HashMap<>();
		MensajeDto mensajeDto;

		try {
			cuentaService.deleteCuenta(cuentaId);
			mensajeDto = new MensajeDto(true, "Registro eliminado");
		} catch (Exception e) {
			responseMap.put(MENSAJE, "Error");
			responseMap.put("mensaje", e.toString());
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MensajeDto>(mensajeDto, HttpStatus.OK);
	}	
}
