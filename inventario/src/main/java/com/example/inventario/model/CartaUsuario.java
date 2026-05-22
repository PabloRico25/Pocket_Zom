package com.example.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "cartas_usuario")
@Data
public class CartaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    @Column(name = "codigo_carta", nullable = false)
    private String codigoCarta;  // RL a Carta (código único)

    private Integer cantidad = 1;

    @Column(name = "fecha_adquisicion")
    private LocalDateTime fechaAdquisicion = LocalDateTime.now();

    @Column(name = "es_favorita")
    private Boolean esFavorita = false;
}