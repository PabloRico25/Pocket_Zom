package com.example.compra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "aperturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jugadorId;
    private Long suministroId;
    private LocalDateTime fecha = LocalDateTime.now();
    @Column(columnDefinition = "TEXT")
    private String cartasObtenidas;
}
