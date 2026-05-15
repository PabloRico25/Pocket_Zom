package com.example.Pocket_Z.modulo_ms.partida.repository;

import com.example.Pocket_Z.modulo_ms.partida.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugador1IdOrJugador2Id(Long jugadorId1, Long jugadorId2);
    List<Partida> findByEstado(String estado);
}
