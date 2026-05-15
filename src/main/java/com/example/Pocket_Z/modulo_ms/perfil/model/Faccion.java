package com.example.Pocket_Z.modulo_ms.perfil.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "facciones")
@Data
public class Faccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "lider_id", nullable = false)
    private Jugador lider;

    private Integer nivelInfeccion = 0;
    private Integer bonoAtributo = 0;
}