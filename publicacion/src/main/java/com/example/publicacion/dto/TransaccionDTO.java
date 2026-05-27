package com.example.publicacion.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransaccionDTO {
    private Long id;
    private Long publicacionId;
    private Long compradorId;
    private LocalDateTime fechaCompra;
}

