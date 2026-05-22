package com.example.logros.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LogroJugadorResponseDTO {
    private Long id;
    private String jugadorId;
    private String idLogro;
    private String nombreLogro;
    private LocalDateTime fechaDesbloqueo;
}