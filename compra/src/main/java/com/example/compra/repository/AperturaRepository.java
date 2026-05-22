package com.example.compra.repository;

import com.example.compra.model.Apertura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AperturaRepository extends JpaRepository<Apertura, Long> {
    List<Apertura> findByJugadorId(Long jugadorId);
}