package com.example.publicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PublicacionResponseDTO {
    private Long id;
    private Long vendedorId;
    private String codigoCarta;
    private Integer precio;
    private String estado;
    private LocalDateTime fechaPublicacion;
}