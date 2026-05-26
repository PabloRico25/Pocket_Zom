package com.example.perfil.repository;

import com.example.perfil.model.Faccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaccionRepository extends JpaRepository<Faccion, Long> {
    Optional<Faccion> findByNombre(String nombre);
}
