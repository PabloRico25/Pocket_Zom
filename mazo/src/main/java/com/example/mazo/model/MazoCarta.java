package com.example.mazo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mazo_cartas")
@Data
@NoArgsConstructor
public class MazoCarta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del mazo es obligatorio")
    @Column(name = "mazo_id")
    private Long mazoId;

    @NotBlank(message = "El código de la carta es obligatorio")
    @Column(name = "codigo_carta")
    private String codigoCarta;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad = 1;
}