package com.example.Pocket_Z.modulo_ms.billetera.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
public class Movimiento {
    @Id
    @Column(name = "id_transaccion")
    private String idTransaccion;

    @ManyToOne
    @JoinColumn(name = "cartera_id")
    private Cartera cartera;

    @Column(nullable = false)
    private String tipo;   // "INGRESO" o "EGRESO"

    private Integer monto;
    private String concepto;   // ej. "Premio duelo", "Compra de suministro"
    private LocalDateTime fecha = LocalDateTime.now();
}