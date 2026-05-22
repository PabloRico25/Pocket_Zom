package com.example.publicacion.model;

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

    @Column(name = "vendedor_id", nullable = false)
    private Long vendedorId;

    @Column(name = "codigo_carta", nullable = false)
    private String codigoCarta;

    private Integer precio;

    private String estado; // "ACTIVA", "VENDIDA"

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
}