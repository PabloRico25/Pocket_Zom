package com.example.billetera.repository;

import com.example.billetera.model.Cartera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarteraRepository extends JpaRepository<Cartera, Long> {
    @Query("SELECT c FROM Cartera c WHERE c.jugadorId = :jugadorId")
    Cartera findByJugadorId(@Param("jugadorId") Long jugadorId);
}