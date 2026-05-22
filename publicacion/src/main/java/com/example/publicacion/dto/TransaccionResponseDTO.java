package com.example.publicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransaccionResponseDTO {
    private Long id;
    private Long publicacionId;
    private String codigoCarta;
    private Integer precio;
    private Long vendedorId;
    private Long compradorId;
    private LocalDateTime fechaCompra;
}