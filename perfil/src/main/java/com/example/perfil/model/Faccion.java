package com.example.perfil.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "facciones")
@Data
public class Faccion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private Long liderId;        // referencia lógica a Jugador
    private Integer nivelInfeccion = 0;
    private Integer bonoAtributo = 0;
}