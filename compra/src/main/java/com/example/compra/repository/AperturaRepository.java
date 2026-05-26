package com.example.compra.repository;

import com.example.compra.model.Apertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AperturaRepository extends JpaRepository<Apertura, Long> {
    List<Apertura> findByJugadorId(Long jugadorId);
}
