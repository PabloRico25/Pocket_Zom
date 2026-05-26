package com.example.logros.repository;

import com.example.logros.model.LogroJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogroJugadorRepository extends JpaRepository<LogroJugador, Long> {
    List<LogroJugador> findByJugadorId(Long jugadorId);
    Optional<LogroJugador> findByJugadorIdAndIdLogro(Long jugadorId, String idLogro);
}