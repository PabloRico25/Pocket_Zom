package com.example.rango.repository;

import com.example.rango.model.Clasificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClasificacionRepository extends JpaRepository<Clasificacion, Long> {
    Optional<Clasificacion> findByJugadorId(Long jugadorId);
    List<Clasificacion> findAllByOrderByPuntosEloDesc();
}