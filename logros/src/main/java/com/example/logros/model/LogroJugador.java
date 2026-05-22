package com.example.logros.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "logro_jugador")
@Data
public class LogroJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jugador_id", length = 30, nullable = false)
    private String jugadorId;  // Referencia lógica (String)

    @ManyToOne
    @JoinColumn(name = "logro_id")
    private Logro logro;

    @Column(name = "fecha_desbloqueo")
    private LocalDateTime fechaDesbloqueo = LocalDateTime.now();
}