// RolRepository
package com.example.Pocket_Z.modulo_ms.perfil.repository;

import com.example.Pocket_Z.modulo_ms.perfil.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}