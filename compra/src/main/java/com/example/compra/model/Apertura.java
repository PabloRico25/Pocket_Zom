package com.example.compra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "aperturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_apertura")
    private Long id;
    @NotNull(message = "El jugador es obligatorio")

    @Column(name = "jugador_id", nullable = false)
    private Long jugadorId;
    @NotNull(message = "El suministro es obligatorio")

    @Column(name = "suministro_id", nullable = false)
    private Long suministroId;

    @Column(name = "fecha")
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "cartas_obtenidas", columnDefinition = "TEXT")
    private String cartasObtenidas;
}
