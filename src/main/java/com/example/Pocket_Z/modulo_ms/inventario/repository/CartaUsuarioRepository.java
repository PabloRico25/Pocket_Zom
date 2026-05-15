package com.example.Pocket_Z.modulo_ms.inventario.repository;

import com.example.Pocket_Z.modulo_ms.inventario.model.CartaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartaUsuarioRepository extends JpaRepository<CartaUsuario, Long> {
    List<CartaUsuario> findByInventarioId(Long inventarioId);
    Optional<CartaUsuario> findByInventarioIdAndCodigoCarta(Long inventarioId, String codigoCarta);
    List<CartaUsuario> findByInventario_JugadorId(Long jugadorId);
}