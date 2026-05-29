package com.example.publicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferirCartaDTO {
    private Long idJugadorOrigen;
    private Long idJugadorDestino;
    private String codigoCarta;
    private Integer cantidad;
}