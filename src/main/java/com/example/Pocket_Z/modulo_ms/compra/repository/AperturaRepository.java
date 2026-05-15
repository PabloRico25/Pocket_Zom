package com.example.Pocket_Z.modulo_ms.compra.repository;

import com.example.Pocket_Z.modulo_ms.compra.model.Apertura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AperturaRepository extends JpaRepository<Apertura, Long> {
    List<Apertura> findByJugadorId(Long jugadorId);
}