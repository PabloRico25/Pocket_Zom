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
    private String tipo;
}