package com.example.Pocket_Z.modulo_ms.compra.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "suministros")
@Data
public class Suministro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;          // ej. "Pack Básico", "Pack Legendario"
    private Integer costo;          // precio en monedas
    private Integer cantidadCartas; // cuántas cartas da al abrir
    private String probabilidades;  // JSON simple, ej: "{\"comun\":0.7,\"rara\":0.2,\"epica\":0.1}"
}