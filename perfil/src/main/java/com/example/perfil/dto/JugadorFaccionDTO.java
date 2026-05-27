package com.example.perfil.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JugadorFaccionDTO {
    private Long id;
    private Long jugadorId;
    private Long faccionId;
    private LocalDateTime fechaIngreso;
}