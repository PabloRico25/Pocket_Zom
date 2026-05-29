package com.example.publicacion.model;

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
@Table(name = "publicaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_publicacion")
    private Long id;
    @NotNull(message = "El ID del vendedor es obligatorio")

    @Column(name = "vendedor_id", nullable = false)
    private Long vendedorId;
    @NotBlank(message = "El código de la carta es obligatorio")
    @Size(max = 20, message = "El código no puede superar 20 caracteres")

    @Column(name = "codigo_carta", nullable = false, length = 20)
    private String codigoCarta;
    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser al menos 1")

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "estado", length = 20)
    private String estado = "ACTIVA";

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();
}
