package com.example.perfil.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RolDTO {
    private Long id;
    @NotBlank
    private String nombre;
}