package com.example.Pocket_Z.modulo_ms.inventario.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "cartas_usuario")
@Data
public class CartaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;

    @Column(nullable = false)
    private String codigoCarta;  // Referencia lógica al MS Catálogo

    private Integer cantidad = 1;

    private LocalDateTime fechaAdquisicion = LocalDateTime.now();

    private Boolean esFavorita = false;
}