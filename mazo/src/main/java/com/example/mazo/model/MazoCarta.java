package com.example.mazo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mazo_cartas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MazoCarta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_mazo_carta")
    private Long idMazoCarta;
    @NotNull(message = "El mazo es obligatorio")

    @Column(name = "id_mazo", nullable = false)
    private Long idMazo;
    @NotBlank(message = "El código de la carta es obligatorio")

    @Column(name = "codigo_carta", nullable = false, length = 20)
    private String codigoCarta;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")

    @Column(name = "cantidad")
    private Integer cantidad = 1;
}
