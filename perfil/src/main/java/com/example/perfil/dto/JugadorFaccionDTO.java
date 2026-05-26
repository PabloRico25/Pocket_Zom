package com.example.perfil.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JugadorFaccionDTO {
    private Long id;
    private Long jugadorId;
    private Long faccionId;
    private LocalDateTime fechaIngreso;
}