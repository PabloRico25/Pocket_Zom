package com.example.publicacion.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicacionDTO {
    private Long id;
    @NotNull
    private Long vendedorId;
    @NotBlank
    private String codigoCarta;
    @NotNull @Min(1)
    private Integer precio;
    private String estado;
    private LocalDateTime fechaPublicacion;
}