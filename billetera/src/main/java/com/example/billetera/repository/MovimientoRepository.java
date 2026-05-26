package com.example.billetera.repository;

import com.example.billetera.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, String> {
    List<Movimiento> findByCarteraIdOrderByFechaDesc(Long carteraId);
}