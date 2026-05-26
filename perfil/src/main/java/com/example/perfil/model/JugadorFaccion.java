package com.example.perfil.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugador_faccion")
@Data
public class JugadorFaccion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jugadorId;      // referencia lógica a Jugador
    private Long faccionId;      // referencia lógica a Faccion
    private LocalDateTime fechaIngreso = LocalDateTime.now();
}