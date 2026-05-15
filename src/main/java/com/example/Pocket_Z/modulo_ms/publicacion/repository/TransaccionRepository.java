package com.example.Pocket_Z.modulo_ms.publicacion.repository;

import com.example.Pocket_Z.modulo_ms.publicacion.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCompradorId(Long compradorId);
    List<Transaccion> findByPublicacion_VendedorId(Long vendedorId);
}