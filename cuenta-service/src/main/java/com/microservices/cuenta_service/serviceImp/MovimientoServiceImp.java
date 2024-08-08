package com.microservices.cuenta_service.serviceImp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.cuenta_service.dao.MovimientoDao;
import com.microservices.cuenta_service.dto.MovimientoDto;
import com.microservices.cuenta_service.dto.MovimientoResponseDto;
import com.microservices.cuenta_service.mode.entities.Cuenta;
import com.microservices.cuenta_service.mode.entities.Movimiento;
import com.microservices.cuenta_service.service.CuentaService;
import com.microservices.cuenta_service.service.MovimientoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovimientoServiceImp implements MovimientoService {

	@Autowired
	MovimientoDao movimientoDao;
	@Autowired
	CuentaService cuentaService;

	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public List<MovimientoDto> listMovimientos() throws Exception {
		var movimiento = movimientoDao.findAll();
		return movimiento.stream().map(this::mapToMovimientoDto).toList();
	}

	@Override
	public MovimientoDto getMomivimientoDto(Long id) throws Exception {
		Movimiento movimiento = getMovimiento(id); 
        String fechaFormat = df.format(movimiento.getFecha());

		return MovimientoDto.builder()
	            .numeroDeCuenta(movimiento.getCuenta().getNumeroCuenta())
	            .tipoMovimiento(movimiento.getTipoMovimiento())
	            .fecha(fechaFormat)
	            .valor(movimiento.getValor()+"")
	            .saldo(movimiento.getSaldo()+"")
	            .build(); 
	}

	@Override
	public void saveDTO(MovimientoDto dto) throws Exception {
		// TODO Auto-generated method stub
		// se debe guardar el movimiento con la entidad de la cuenta
		LocalDateTime now = LocalDateTime.now();
		Cuenta cuenta = cuentaService.getCuentaByNumeroCuenta(dto.getNumeroDeCuenta());
		
		BigDecimal saldo = new BigDecimal(dto.getSaldo());
		BigDecimal valor = new BigDecimal(dto.getValor());
		if(dto.getTipoMovimiento().equals("Retiro")) {
		    saldo = saldo.subtract(valor);
		} else {
		    saldo = saldo.add(valor);
		}
		
		Movimiento movimiento = new Movimiento();
		movimiento.setFecha(now);
		movimiento.setValor(new BigDecimal(dto.getValor()));
		movimiento.setTipoMovimiento(dto.getTipoMovimiento());
		movimiento.setCuenta(cuenta);
		movimiento.setSaldo(saldo);
		movimientoDao.save(movimiento);
	}

	@Override
	public void deleteMovimiento(Long id) throws Exception {
		try {
			Movimiento movimiento = getMovimiento(id);
			movimientoDao.delete(movimiento);
		} catch (Exception e) {
			throw new Exception("error: " + e.getMessage());
		}
	}

	@Override
	public void updateMovimiento(Long id, MovimientoDto movimientoDto) throws Exception {
		Movimiento movimiento = getMovimiento(id);
		movimiento.setTipoMovimiento(movimientoDto.getTipoMovimiento());
		movimiento.setValor(new BigDecimal(movimientoDto.getValor()));;
		movimientoDao.save(movimiento);
	}

	private MovimientoDto mapToMovimientoDto(Movimiento movimiento) {
		
        String fechaFormat = df.format(movimiento.getFecha());
        
		return MovimientoDto.builder()
				.clienteId(movimiento.getCuenta().getClienteId() + "")
				.tipoMovimiento(movimiento.getTipoMovimiento())
				.valor(movimiento.getValor() + "")
				.fecha(fechaFormat)
				.build();
	}
	
	public Movimiento getMovimiento(Long id) throws Exception {
		return movimientoDao.getReferenceById(id);
	}

	@Override
	public List<MovimientoResponseDto> getReportePorUsuario() throws Exception {
		// TODO Auto-generated method stub
		// return movimientoDao.findCustomData();
		return null;
	}
}
