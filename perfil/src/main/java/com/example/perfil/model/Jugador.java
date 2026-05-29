package com.example.perfil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jugadores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_jugador")
    private Long idJugador;
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")

    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 255, message = "La contraseña debe tener al menos 4 caracteres")

    @Column(name = "password", nullable = false)
    private String password;
    @Min(value = 0, message = "El nivel no puede ser negativo")

    @Column(name = "nivel")
    private Integer nivel = 1;
    @Column(name = "id_rol", nullable = false)
    private Long idRol;
}