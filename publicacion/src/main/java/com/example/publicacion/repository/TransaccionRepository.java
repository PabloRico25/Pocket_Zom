package com.example.publicacion.repository;

import com.example.publicacion.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCompradorId(Long compradorId);
    List<Transaccion> findByPublicacion_VendedorId(Long vendedorId);
}