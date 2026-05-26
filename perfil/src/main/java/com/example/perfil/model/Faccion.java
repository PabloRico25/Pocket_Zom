package com.example.perfil.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "facciones")
@Data
public class Faccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la facción es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar 50 caracteres")
    @Column(unique = true, nullable = false, length = 50)
    private String nombre;

    @NotNull(message = "El líder es obligatorio")
    private Long liderId;

    @Min(value = 0, message = "El nivel de infección no puede ser negativo")
    private Integer nivelInfeccion = 0;

    @Min(value = 0, message = "El bono de atributo no puede ser negativo")
    private Integer bonoAtributo = 0;
}