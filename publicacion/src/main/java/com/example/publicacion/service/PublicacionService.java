package com.example.publicacion.service;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.repository.PublicacionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<PublicacionDTO> listarActivas() {
        return publicacionRepository.findByEstado("ACTIVA").stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<PublicacionDTO> listarPorVendedor(Long vendedorId) {
        return publicacionRepository.findByVendedorId(vendedorId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PublicacionDTO obtenerPorId(Long id) {
        return publicacionRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public PublicacionDTO crearPublicacion(PublicacionDTO dto) {
        Publicacion p = new Publicacion();
        p.setVendedorId(dto.getVendedorId());
        p.setCodigoCarta(dto.getCodigoCarta().trim().toUpperCase());
        p.setPrecio(dto.getPrecio());
        p.setEstado("ACTIVA");
        p.setFechaPublicacion(LocalDateTime.now());
        p = publicacionRepository.save(p);
        return toDTO(p);
    }

    public void eliminarPublicacion(Long id) {
        Publicacion p = publicacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        if ("VENDIDA".equals(p.getEstado())) {
            throw new RuntimeException("No se puede eliminar una publicación ya vendida");
        }
        publicacionRepository.delete(p);
    }

    public Publicacion marcarComoVendida(Long id) {
        Publicacion p = publicacionRepository.findByIdAndEstado(id, "ACTIVA")
                .orElseThrow(() -> new RuntimeException("Publicación no activa"));
        p.setEstado("VENDIDA");
        return publicacionRepository.save(p);
    }

    private PublicacionDTO toDTO(Publicacion p) {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(p.getId());
        dto.setVendedorId(p.getVendedorId());
        dto.setCodigoCarta(p.getCodigoCarta());
        dto.setPrecio(p.getPrecio());
        dto.setEstado(p.getEstado());
        dto.setFechaPublicacion(p.getFechaPublicacion());
        return dto;
    }
}

