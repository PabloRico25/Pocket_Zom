package com.example.cartacatalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cartas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 20)
    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 50)
    private String raza;

    @Min(0)
    private Integer ataque = 0;

    @Min(0)
    private Integer defensa = 0;

    @Min(0)
    private Integer coste = 0;

    @Size(max = 500)
    private String habilidad;

    private Boolean activa = true;
}