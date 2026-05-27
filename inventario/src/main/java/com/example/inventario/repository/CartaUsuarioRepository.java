package com.example.inventario.repository;

import com.example.inventario.model.CartaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface CartaUsuarioRepository extends JpaRepository<CartaUsuario, Long> {
    List<CartaUsuario> findByIdInventario(Long idInventario);
    Optional<CartaUsuario> findByIdInventarioAndCodigoCarta(Long idInventario, String codigoCarta);
}
