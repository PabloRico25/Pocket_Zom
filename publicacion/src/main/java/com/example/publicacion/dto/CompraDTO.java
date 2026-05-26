package com.example.publicacion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompraDTO {
    @NotNull
    private Long publicacionId;
}
