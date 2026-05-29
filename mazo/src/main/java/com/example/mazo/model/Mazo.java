package com.example.mazo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mazos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mazo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_mazo")
    private Long idMazo;
    @NotNull(message = "El jugador es obligatorio")

    @Column(name = "id_jugador", nullable = false)
    private Long idJugador;
    @NotBlank(message = "El nombre del mazo es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "es_activo")
    private Boolean esActivo = false;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}