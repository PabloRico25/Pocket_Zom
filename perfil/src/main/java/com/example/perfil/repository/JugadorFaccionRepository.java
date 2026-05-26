package com.example.perfil.repository;

import com.example.perfil.model.JugadorFaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JugadorFaccionRepository extends JpaRepository<JugadorFaccion, Long> {
    List<JugadorFaccion> findByJugadorId(Long jugadorId);
    Optional<JugadorFaccion> findByJugadorIdAndFaccionId(Long jugadorId, Long faccionId);
}
