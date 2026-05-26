package com.example.logros.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogroJugadorDTO {
    private Long id;
    private Long jugadorId;
    private String idLogro;
    private String nombreLogro;
    private LocalDateTime fechaDesbloqueo;
}
