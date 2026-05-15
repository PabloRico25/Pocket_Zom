package com.example.Pocket_Z.modulo_ms.mazo.model;

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
    @JoinColumn(name = "mazo_id")
    private Mazo mazo;

    @Column(nullable = false)
    private String codigoCarta;   // referencia lógica a Carta (MS Catálogo)

    private Integer cantidad = 1;
}