package com.example.cartacatalogo.repository;

import com.example.cartacatalogo.model.Carta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartaRepository extends JpaRepository<Carta, Long> {
    Optional<Carta> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Carta> findByCoste(Integer coste);
    List<Carta> findByActivaTrue();
}
