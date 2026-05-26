package com.example.perfil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class JugadorDTO {
    private Long id;
    @NotBlank
    private String nombreUsuario;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    private Integer nivel;
    private Long rolId;
    private String rolNombre;
    private LocalDateTime fechaRegistro;
}