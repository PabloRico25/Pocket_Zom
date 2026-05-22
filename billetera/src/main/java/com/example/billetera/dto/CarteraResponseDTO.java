package com.example.billetera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarteraResponseDTO {
    private Long id;
    private Long jugadorId;
    private Integer saldo;
}