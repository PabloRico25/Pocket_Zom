package com.example.perfil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JugadorRequestDTO {
    @NotBlank
    private String nombreUsuario;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    private Long rolId;   // opcional, por defecto se asigna ROLE_PLAYER
}