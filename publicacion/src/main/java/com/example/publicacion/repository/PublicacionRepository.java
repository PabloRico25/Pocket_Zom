package com.example.publicacion.repository;

import com.example.publicacion.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByEstado(String estado);
    List<Publicacion> findByVendedorId(Long vendedorId);
    Optional<Publicacion> findByIdAndEstado(Long id, String estado);
}
