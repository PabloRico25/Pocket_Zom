package com.example.Pocket_Z.modulo_ms.billetera.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "billeteras")
@Data
public class Billetera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBilletera;

    @Column(nullable = false, unique = true)
    private Long idJugador;

    private Integer saldo;
    private Integer monedasJuego;
}
