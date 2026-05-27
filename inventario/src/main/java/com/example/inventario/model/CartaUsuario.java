package com.example.inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cartas_usuario")
@Data
@NoArgsConstructor
public class CartaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del inventario es obligatorio")
    private Long inventarioId;

    @NotBlank(message = "El código de la carta es obligatorio")
    @Size(max = 20, message = "El código de carta no puede superar 20 caracteres")
    private String codigoCarta;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad = 1;

    @PastOrPresent(message = "La fecha de adquisición no puede ser futura")
    private LocalDateTime fechaAdquisicion = LocalDateTime.now();

    private Boolean esFavorita = false;
}