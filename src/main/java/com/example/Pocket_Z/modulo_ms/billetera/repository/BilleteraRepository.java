package com.example.Pocket_Z.modulo_ms.billetera.repository;

import com.example.Pocket_Z.modulo_ms.billetera.model.Billetera;
import com.example.Pocket_Z.modulo_ms.billetera.model.Cartera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BilleteraRepository extends JpaRepository<Billetera, Long> {
    Optional<Billetera> findByIdJugador(Long idJugador);
}
