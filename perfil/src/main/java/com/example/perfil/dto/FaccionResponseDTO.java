package com.example.perfil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FaccionResponseDTO {
    private Long id;
    private String nombre;
    private Long liderId;
    private String liderNombre;
    private Integer nivelInfeccion;
    private Integer bonoAtributo;
}