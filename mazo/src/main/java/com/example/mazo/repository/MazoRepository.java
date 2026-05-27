package com.example.mazo.repository;

import com.example.mazo.model.Mazo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MazoRepository extends JpaRepository<Mazo, Long> {
    List<Mazo> findByIdJugador(Long idJugador);
    Optional<Mazo> findByIdJugadorAndEsActivoTrue(Long idJugador);
}