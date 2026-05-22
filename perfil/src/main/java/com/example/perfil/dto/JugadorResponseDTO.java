package com.example.perfil.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JugadorResponseDTO {
    private Long id;
    private String nombreUsuario;
    private String email;
    private Integer nivel;
    private String rolNombre;
    private LocalDateTime fechaRegistro;
}