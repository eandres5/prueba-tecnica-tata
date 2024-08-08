package com.microservices.cuenta_service.serviceImp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.cuenta_service.dao.CuentaDao;
import com.microservices.cuenta_service.dto.BaseReponse;
import com.microservices.cuenta_service.dto.CuentaDto;
import com.microservices.cuenta_service.mode.entities.Cuenta;
import com.microservices.cuenta_service.service.CuentaService;

import io.netty.handler.codec.http.HttpContentEncoder.Result;
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
	public void updateCliente(Long clienteId, CuentaDto cuentaDto) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public List<CuentaDto> listCuentas() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CuentaDto getCuentaDto(Long cuentaId) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void deleteCliente(Long clienteId) throws Exception {
		// TODO Auto-generated method stub

	}

	private void save(Cuenta cuenta) throws Exception {
		try {
			cuentaDao.save(cuenta);
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}
	

}
