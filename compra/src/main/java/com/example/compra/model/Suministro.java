package com.example.compra.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "suministros")
@Data
public class Suministro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer costo;
    private Integer cantidadCartas;

    @Column(columnDefinition = "TEXT")
    private String probabilidades; // JSON
}