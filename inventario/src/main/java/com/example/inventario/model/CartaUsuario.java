package com.example.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cartas_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carta_usuario")
    private Long idCartaUsuario;
    @NotNull(message = "El ID del inventario es obligatorio")
    @Column(name = "id_inventario", nullable = false)
    private Long idInventario;
    @NotBlank(message = "El código de la carta es obligatorio")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")
    @Column(name = "codigo_carta", nullable = false, length = 20)
    private String codigoCarta;
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(name = "cantidad")
    private Integer cantidad = 1;
    @Column(name = "es_favorita")
    private Boolean esFavorita = false;
    @Column(name = "fecha_adquisicion")
    private LocalDateTime fechaAdquisicion = LocalDateTime.now();
}
