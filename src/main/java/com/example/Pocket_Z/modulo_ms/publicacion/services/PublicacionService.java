package com.example.Pocket_Z.modulo_ms.publicacion.services;

import com.example.Pocket_Z.modulo_ms.publicacion.model.Publicacion;
import com.example.Pocket_Z.modulo_ms.publicacion.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionService {
    private final PublicacionRepository publicacionRepository;

    public List<Publicacion> listarActivas() {
        return publicacionRepository.findByEstado("ACTIVA");
    }

    public List<Publicacion> listarPorVendedor(Long vendedorId) {
        return publicacionRepository.findByVendedorId(vendedorId);
    }

    public Publicacion guardar(Publicacion publicacion) {
        publicacion.setId(null);
        publicacion.setEstado("ACTIVA");
        publicacion.setFechaPublicacion(java.time.LocalDateTime.now());
        return publicacionRepository.save(publicacion);
    }

    public void eliminar(Long id) {
        publicacionRepository.deleteById(id);
    }

    public Publicacion marcarComoVendida(Long id) {
        Publicacion pub = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        pub.setEstado("VENDIDA");
        return publicacionRepository.save(pub);
    }
}