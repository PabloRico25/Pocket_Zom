package com.example.compra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suministros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suministro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suministro")
    private Long id;
    @NotBlank(message = "El nombre del suministro es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @NotNull(message = "El costo es obligatorio")
    @Min(value = 1, message = "El costo debe ser al menos 1")
    @Column(name = "costo", nullable = false)
    private Integer costo;
    @NotNull(message = "La cantidad de cartas es obligatoria")
    @Min(value = 1, message = "Debe entregar al menos 1 carta")
    @Column(name = "cantidad_cartas", nullable = false)
    private Integer cantidadCartas;
    @Column(name = "probabilidades", columnDefinition = "TEXT")
    private String probabilidades;
}
