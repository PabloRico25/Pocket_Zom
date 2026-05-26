package com.example.compra.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AbrirSobreDTO {
    @NotNull
    private Long suministroId;
}
