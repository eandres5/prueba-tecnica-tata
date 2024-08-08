package com.microservices.cuenta_service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservices.cuenta_service.dto.MovimientoResponseDto;
import com.microservices.cuenta_service.mode.entities.Movimiento;

@Repository
public interface MovimientoDao extends JpaRepository<Movimiento, Long>{
	
	@Query(name = "Movimiento.findCustomData")
    List<MovimientoResponseDto> findCustomData();
}