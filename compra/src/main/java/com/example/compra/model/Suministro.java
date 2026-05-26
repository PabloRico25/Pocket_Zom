package com.example.compra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suministros")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suministro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer costo;
    private Integer cantidadCartas;
    @Column(columnDefinition = "TEXT")
    private String probabilidades;
}
