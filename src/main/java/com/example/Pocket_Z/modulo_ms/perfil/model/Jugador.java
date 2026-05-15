package com.example.Pocket_Z.modulo_ms.perfil.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jugadores")
@Data
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt se aplicará antes de guardar

    private Integer nivel = 1;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
}