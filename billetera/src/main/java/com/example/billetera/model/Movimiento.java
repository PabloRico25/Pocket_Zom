package com.example.billetera.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    @Id
    @Column(name = "id_transaccion")
    private String idTransaccion;

    @ManyToOne
    @JoinColumn(name = "cartera_id", nullable = false)
    private Cartera cartera;

    private Integer monto;
    private String concepto;
    private LocalDateTime fecha;
    private String tipo; // para el postman ingresar: INGRESO o EGRESO
    private Long carteraId;

    @Column(name = "tipo_movimiento", insertable = false, updatable = false)
    private String tipoMovimiento;

    @Column(insertable = false, updatable = false)
    private String descripcion;

    @Column(name = "billeteras_id_billetera", insertable = false, updatable = false)
    private String billeterasIdBilletera;

    @Column(name = "jugador_id_jugador", insertable = false, updatable = false)
    private String jugadorIdViejo;
}
