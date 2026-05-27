package com.example.publicacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long id;
    @NotNull(message = "La publicación es obligatoria")
    @Column(name = "publicacion_id", nullable = false)
    private Long publicacionId;
    @NotNull(message = "El comprador es obligatorio")
    @Column(name = "comprador_id", nullable = false)
    private Long compradorId;
    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra = LocalDateTime.now();
}