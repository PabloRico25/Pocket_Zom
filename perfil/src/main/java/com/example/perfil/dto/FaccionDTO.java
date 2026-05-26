package com.example.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FaccionDTO {
    private Long id;
    @NotBlank
    private String nombre;
    private Long liderId;
    private String liderNombre;
    private Integer nivelInfeccion;
    private Integer bonoAtributo;
}