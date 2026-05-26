package com.example.billetera.repository;

import com.example.billetera.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, String> {
    @Query("SELECT m FROM Movimiento m WHERE m.cartera.id = :carteraId ORDER BY m.fecha DESC")
    List<Movimiento> findByCarteraIdOrderByFechaDesc(@Param("carteraId") Long carteraId);
}