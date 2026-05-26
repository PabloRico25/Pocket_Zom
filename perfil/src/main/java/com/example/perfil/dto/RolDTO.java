package com.example.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolDTO {
    private Long id;
    @NotBlank
    private String nombre;
}