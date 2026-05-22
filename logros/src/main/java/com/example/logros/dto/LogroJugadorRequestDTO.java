package com.example.logros.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogroJugadorRequestDTO {
    @NotBlank
    private String jugadorId;
    @NotBlank
    private String idLogro;
}