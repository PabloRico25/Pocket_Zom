package com.example.Pocket_Z.modulo_ms.publicacion.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicaciones")
@Data
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long vendedorId;   // referencia lógica a Jugador

    @Column(nullable = false)
    private String codigoCarta; // referencia lógica a Carta (MS Catálogo)

    private Integer precio;
    private String estado = "ACTIVA";  // ACTIVA, VENDIDA
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
}
