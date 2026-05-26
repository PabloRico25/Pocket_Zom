package com.example.partida.repository;

import com.example.partida.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugador1IdOrJugador2Id(Long jugadorId1, Long jugadorId2);

}
