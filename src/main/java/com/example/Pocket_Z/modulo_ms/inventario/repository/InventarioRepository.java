package com.example.Pocket_Z.modulo_ms.inventario.repository;

import com.example.Pocket_Z.modulo_ms.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByJugadorId(Long jugadorId);
}