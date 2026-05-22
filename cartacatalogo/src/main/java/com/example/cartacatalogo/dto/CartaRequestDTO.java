package com.example.cartacatalogo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CartaRequestDTO {
    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String raza;

    @PositiveOrZero(message = "El ataque debe ser ≥ 0")
    private Integer ataque;

    @PositiveOrZero(message = "La defensa debe ser ≥ 0")
    private Integer defensa;

    @PositiveOrZero(message = "El coste debe ser ≥ 0")
    private Integer coste;

    private String habilidad;

    private Boolean activa;
}