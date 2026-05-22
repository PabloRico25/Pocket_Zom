package com.example.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FaccionRequestDTO {
    @NotBlank
    private String nombre;
    private Long liderId;
    private Integer nivelInfeccion;
    private Integer bonoAtributo;
}