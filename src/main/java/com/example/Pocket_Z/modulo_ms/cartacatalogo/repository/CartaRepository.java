package com.example.Pocket_Z.modulo_ms.cartacatalogo.repository;

import com.example.Pocket_Z.modulo_ms.cartacatalogo.model.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartaRepository extends JpaRepository<Carta, Long> {
    Optional<Carta> findByCodigoCarta(String codigoCarta);
    List<Carta> findByRaza(String raza);
    List<Carta> findByEstaActivaTrue();
}