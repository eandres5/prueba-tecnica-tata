package com.microservices.cuenta_service.serviceImp;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.cuenta_service.dao.CuentaDao;
import com.microservices.cuenta_service.dto.CuentaDto;
import com.microservices.cuenta_service.dto.CuentaEditDto;
import com.microservices.cuenta_service.mode.entities.Cuenta;
import com.microservices.cuenta_service.service.CuentaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentaServiceImp implements CuentaService {

	@Autowired
	CuentaDao cuentaDao;

	@Autowired
    private WebClient.Builder webClientBuilder;
	
	@Override
	public List<CuentaDto> listCuentas() throws Exception {
		var cuentas = cuentaDao.findAll();
		return cuentas.stream().map(this::mapToCuentaDto).toList();
	}

	@Override
	public CuentaDto getCuentaDto(Long cuentaId) throws Exception {
		Cuenta cuenta = getCuenta(cuentaId); 
		return CuentaDto.builder()
	            .clienteId(cuenta.getClienteId()+"")
	            .numeroCuenta(cuenta.getNumeroCuenta())
	            .tipoCuenta(cuenta.getTipoCuenta())
	            .saldo(cuenta.getSaldoInicial()+"")
	            .build(); 
	}

	@Override
	public void saveDTO(CuentaDto dto) throws Exception {
		try {			
//			 Validar la existencia del cliente
			boolean result = webClientBuilder.build()
	                .get()
	                .uri("lb://cliente-service/clientes/isCliente/" + dto.getClienteId())
	                .retrieve()
	                .bodyToMono(boolean.class)
	                .block() != null;

	        if (result) {
	        	Cuenta cuenta = new Cuenta();
				cuenta.setNumeroCuenta(dto.getNumeroCuenta());
				cuenta.setEstado(Boolean.parseBoolean(dto.getEstado()));
				cuenta.setTipoCuenta(dto.getTipoCuenta());
				cuenta.setClienteId(Long.parseLong(dto.getClienteId()));
				cuenta.setSaldoInicial(new BigDecimal(dto.getSaldo()));
				save(cuenta);
	        } else {
	        	throw new RuntimeException("Cliente no encontrado");
	        }
			
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}

	private void save(Cuenta cuenta) throws Exception {
		try {
			cuentaDao.save(cuenta);
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}

	@Override
	public void deleteCuenta(Long cuentaId) throws Exception {
		try {
			Cuenta cuenta = getCuenta(cuentaId);
			cuentaDao.delete(cuenta);
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}

	@Override
	public void updateCuenta(Long cuentaId, CuentaEditDto cuentaDto) throws Exception {
		Cuenta cuenta = getCuenta(cuentaId);
		cuenta.setSaldoInicial(new BigDecimal(cuentaDto.getSaldoInicial()));
		cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
		cuenta.setEstado(Boolean.parseBoolean(cuentaDto.getEstado()));
		cuentaDao.save(cuenta);
	}
	
	public Cuenta getCuenta(Long cuentaId) throws Exception {
		return cuentaDao.getReferenceById(cuentaId);
	}

	private CuentaDto mapToCuentaDto(Cuenta cuenta) {
		return CuentaDto.builder()
	            .clienteId(cuenta.getClienteId()+"")
	            .numeroCuenta(cuenta.getNumeroCuenta())
	            .tipoCuenta(cuenta.getTipoCuenta())
	            .saldo(cuenta.getSaldoInicial()+"")
	            .build();
	}

	@Override
	public Cuenta getCuentaByNumeroCuenta(String numeroCuenta) throws Exception {
		Optional<Cuenta> cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);
	    if (cuenta != null) {
	        Cuenta cuentaEntity = cuenta.get();
	        return cuentaEntity;
	    } else {
	        throw new Exception("Cuenta no encontrada");
	    }
	}
}
