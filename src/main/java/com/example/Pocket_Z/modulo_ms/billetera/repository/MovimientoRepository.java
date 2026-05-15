package com.example.Pocket_Z.modulo_ms.billetera.repository;

import com.example.Pocket_Z.modulo_ms.billetera.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCarteraIdOrderByFechaDesc(Long carteraId);
}