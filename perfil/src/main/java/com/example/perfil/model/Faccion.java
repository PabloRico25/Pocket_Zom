package com.example.perfil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_faccion")
    private Long idFaccion;
    @NotBlank(message = "El nombre de la facción es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    @NotNull(message = "El líder es obligatorio")

    @Column(name = "id_lider", nullable = false)
    private Long idLider;
    @Min(value = 0, message = "El nivel de infección no puede ser negativo")

    @Column(name = "nivel_infeccion")
    private Integer nivelInfeccion = 0;
    @Min(value = 0, message = "El bono de atributo no puede ser negativo")

    @Column(name = "bono_atributo")
    private Integer bonoAtributo = 0;
}