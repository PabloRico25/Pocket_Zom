package com.example.publicacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "publicaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendedorId;
    private String codigoCarta;
    private Integer precio;
    private String estado;
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
}
