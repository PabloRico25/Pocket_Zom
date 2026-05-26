package com.example.mazo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "mazo_cartas")
@Data
public class MazoCarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del mazo es obligatorio")
    private Long mazoId;   // referencia lógica a Mazo

    @NotBlank(message = "El código de la carta es obligatorio")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")
    private String codigoCarta;   // referencia lógica a Carta (MS cartacatalogo)

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad = 1;
}