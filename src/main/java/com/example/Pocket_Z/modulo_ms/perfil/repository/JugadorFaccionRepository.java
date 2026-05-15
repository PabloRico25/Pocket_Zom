// JugadorFaccionRepository
package com.example.Pocket_Z.modulo_ms.perfil.repository;

import com.example.Pocket_Z.modulo_ms.perfil.model.JugadorFaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JugadorFaccionRepository extends JpaRepository<JugadorFaccion, Long> {
    List<JugadorFaccion> findByJugadorId(Long jugadorId);
}