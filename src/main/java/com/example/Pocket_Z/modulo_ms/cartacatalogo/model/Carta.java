package com.example.Pocket_Z.modulo_ms.cartacatalogo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cartas")
@Data
public class Carta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    private String raza;
    private Integer ataque;
    private Integer defensa;
    private Integer coste;
    private String habilidad;
    private Boolean activa = true;
}