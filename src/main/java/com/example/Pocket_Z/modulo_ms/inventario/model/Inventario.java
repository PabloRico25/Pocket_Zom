package com.example.Pocket_Z.modulo_ms.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventarios")
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long jugadorId;  // Referencia lógica al MS de Perfiles

    private LocalDateTime fechaCreacion = LocalDateTime.now();
}