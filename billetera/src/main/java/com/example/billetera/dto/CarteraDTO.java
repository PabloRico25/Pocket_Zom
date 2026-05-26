package com.example.billetera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteraDTO {
    private Long id;
    private Long jugadorId;
    private Integer saldo;
}
