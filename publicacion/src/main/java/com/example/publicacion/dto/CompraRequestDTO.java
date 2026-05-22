package com.example.publicacion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompraRequestDTO {
    @NotNull
    private Long publicacionId;
}