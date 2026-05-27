package com.example.billetera.repository;

import com.example.billetera.model.Cartera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteraRepository extends JpaRepository<Cartera, Long> {

    Optional<Cartera> findByIdJugador(Long idJugador);
}
