package com.example.cartacatalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartaResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String raza;
    private Integer ataque;
    private Integer defensa;
    private Integer coste;
    private String habilidad;
    private Boolean activa;
}