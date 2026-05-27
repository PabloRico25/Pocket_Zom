package com.example.billetera.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarteraDTO {
    private Long id;
    private Long jugadorId;
    private Integer saldo;
}