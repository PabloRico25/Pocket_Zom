package com.example.mazo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MazoCartaResponseDTO {
    private Long id;
    private String codigoCarta;
    private Integer cantidad;
}