package com.microservices.cuenta_service.mode.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="movimiento", schema = "public")
@NamedQuery(
	    name = "Movimiento.findCustomData",
	    query = "SELECT new com.tu.paquete.MovimientoResponseDto(m.fecha, p.nombre, c.numeroCuenta, c.tipoCuenta, m.valor, c.estado, m.tipoMovimiento, m.saldo) " +
	            "FROM Movimiento m " +
	            "JOIN m.cuenta c " +
	            "JOIN c.cliente cl " +
	            "JOIN cl.persona p")
public class Movimiento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long movimientoId;
	@Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
	private BigDecimal saldo;
	@Column(name="tipo_movimiento")
	private String tipoMovimiento;
	private BigDecimal valor;
	//bi-directional many-to-one association to Cuenta
	@ManyToOne
	@JoinColumn(name="cuenta_id")
	private Cuenta cuenta;
}
