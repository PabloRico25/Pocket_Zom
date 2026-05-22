package com.example.cartacatalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Table(name = "cartas")
@Data
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    private String raza;

    @PositiveOrZero
    private Integer ataque;

    @PositiveOrZero
    private Integer defensa;

    @PositiveOrZero
    private Integer coste;

    private String habilidad;

    private Boolean activa = true;
}