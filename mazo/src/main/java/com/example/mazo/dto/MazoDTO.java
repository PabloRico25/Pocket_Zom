package com.example.mazo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MazoDTO {
    private Long id;
    private Long jugadorId;
    @NotBlank(message = "El nombre del mazo es obligatorio")
    private String nombre;
    private Boolean esActivo;
    private LocalDateTime fechaCreacion;
}