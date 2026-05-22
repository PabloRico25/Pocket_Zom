package com.example.mazo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mazo_cartas")
@Data
public class MazoCarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mazo_id", nullable = false)
    private Mazo mazo;

    @Column(name = "codigo_carta", nullable = false)
    private String codigoCarta;  // RL a Carta

    private Integer cantidad = 1;
}