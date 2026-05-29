package com.example.publicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionDTO {
    private Long id;
    private Long publicacionId;
    private Long compradorId;
    private LocalDateTime fechaCompra;
}