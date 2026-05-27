package com.example.perfil.repository;

import com.example.perfil.model.JugadorFaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JugadorFaccionRepository extends JpaRepository<JugadorFaccion, Long> {
    List<JugadorFaccion> findByIdJugador(Long idJugador);
    Optional<JugadorFaccion> findByIdJugadorAndIdFaccion(Long idJugador, Long idFaccion);
}