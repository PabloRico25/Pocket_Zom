package com.example.logros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logros_jugador")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogroJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jugadorId;            // referencia lógica a Jugador
    private String idLogro;            // referencia lógica a Logro
    private LocalDateTime fechaDesbloqueo = LocalDateTime.now();
}
