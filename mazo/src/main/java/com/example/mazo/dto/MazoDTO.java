package com.example.mazo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MazoDTO {
    private Long id;
    private Long jugadorId;

    @NotBlank(message = "El nombre del mazo es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    private String nombre;

    private Boolean esActivo;
    private LocalDateTime fechaCreacion;
}