package com.example.logros.repository;

import com.example.logros.model.LogroJugador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LogroJugadorRepository extends JpaRepository<LogroJugador, Long> {
    List<LogroJugador> findByJugadorId(String jugadorId);
    Optional<LogroJugador> findByJugadorIdAndLogro_IdLogro(String jugadorId, String idLogro);
}