package com.example.cartacatalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "cartas")
@Data
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")
    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 50, message = "La raza no puede superar 50 caracteres")
    private String raza;

    @Min(value = 0, message = "El ataque no puede ser negativo")
    private Integer ataque;

    @Min(value = 0, message = "La defensa no puede ser negativa")
    private Integer defensa;

    @Min(value = 0, message = "El coste no puede ser negativo")
    private Integer coste;

    @Size(max = 500, message = "La habilidad no puede superar 500 caracteres")
    private String habilidad;

    private Boolean activa = true;
}