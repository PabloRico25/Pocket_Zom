package com.example.perfil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugadores")
@Data
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 50, message = "El nombre de usuario no puede superar 50 caracteres")
    @Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
    private String nombreUsuario;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato válido")
    @Size(max = 100, message = "El email no puede superar 100 caracteres")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 255, message = "La contraseña debe tener entre 4 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String password;

    @Min(value = 1, message = "El nivel mínimo es 1")
    @Max(value = 999, message = "El nivel máximo es 999")
    private Integer nivel = 1;

    @NotNull(message = "El rol es obligatorio")
    private Long rolId;

    @PastOrPresent(message = "La fecha de registro no puede ser futura")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}