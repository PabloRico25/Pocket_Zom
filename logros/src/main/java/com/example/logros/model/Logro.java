package com.example.logros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "logros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logro {
    @Id

    @Column(length = 30)
    private String idLogro;
    private String nombre;
    private String descripcion;
    private String condicionTipo;
    private Integer condicionValor;
    private Integer recompensaMonedas = 0;
    private Integer recompensaExp = 0;
}
