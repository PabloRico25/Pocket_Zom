package com.example.perfil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugadores")
@Data
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password; // BCrypt

    private Integer nivel = 1;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    private LocalDateTime fechaRegistro = LocalDateTime.now();
}