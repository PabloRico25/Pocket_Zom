package com.example.logros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "logro")
@Data
public class Logro {
    @Id
    @Column(name = "id_logro", length = 30)
    private String idLogro;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @NotBlank
    @Column(name = "condicion_tipo", length = 30, nullable = false)
    private String condicionTipo;  // "VICTORIAS", "CARTAS_COLECCIONADAS", "EXPERIENCIA"

    @NotNull
    @Column(name = "condicion_valor", nullable = false)
    private Integer condicionValor;

    @Column(name = "recompensa_monedas")
    private Integer recompensaMonedas = 0;

    @Column(name = "recompensa_exp")
    private Integer recompensaExp = 0;
}