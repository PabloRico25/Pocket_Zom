package com.example.logros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogroRequestDTO {
    @NotBlank
    private String idLogro;
    @NotBlank
    private String nombre;
    private String descripcion;
    @NotBlank
    private String condicionTipo;
    @NotNull
    private Integer condicionValor;
    private Integer recompensaMonedas;
    private Integer recompensaExp;
}