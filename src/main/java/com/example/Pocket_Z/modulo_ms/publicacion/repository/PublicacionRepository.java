package com.example.Pocket_Z.modulo_ms.publicacion.repository;

import com.example.Pocket_Z.modulo_ms.publicacion.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByEstado(String estado);
    List<Publicacion> findByVendedorId(Long vendedorId);
}