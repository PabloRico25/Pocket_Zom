// JugadorRepository
package com.example.Pocket_Z.modulo_ms.perfil.repository;

import com.example.Pocket_Z.modulo_ms.perfil.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    Optional<Jugador> findByNombreUsuario(String nombreUsuario);
    Optional<Jugador> findByEmail(String email);
}