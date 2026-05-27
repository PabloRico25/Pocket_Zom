package com.example.publicacion.service;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    public List<PublicacionDTO> listarActivas() {
        return publicacionRepository.findByEstado("ACTIVA").stream()
                .map(this::toDTO).collect(Collectors.toList());
    }
    public List<PublicacionDTO> listarPorVendedor(Long idVendedor) {
        return publicacionRepository.findByVendedorId(idVendedor).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }
    public PublicacionDTO buscarPorId(Long id) {
        return publicacionRepository.findById(id).map(this::toDTO).orElse(null);
    }
    public PublicacionDTO crear(PublicacionDTO dto) {
        Publicacion p = new Publicacion();
        p.setVendedorId(dto.getVendedorId());
        p.setCodigoCarta(dto.getCodigoCarta().trim().toUpperCase());
        p.setPrecio(dto.getPrecio());
        p.setEstado("ACTIVA");
        p.setFechaPublicacion(LocalDateTime.now());
        p = publicacionRepository.save(p);
        log.info("Publicación creada: {}", p.getId());
        return toDTO(p);
    }
    public void eliminar(Long id) {
        Publicacion p = publicacionRepository.findById(id).orElse(null);
        if (p == null) {
            log.warn("Publicación no encontrada: {}", id);
            return;
        }
        if ("VENDIDA".equals(p.getEstado())) {
            log.warn("No se puede eliminar publicación ya vendida: {}", id);
            return;
        }
        publicacionRepository.delete(p);
        log.info("Publicación eliminada: {}", id);
    }
    public Publicacion marcarComoVendida(Long id) {
        Publicacion p = publicacionRepository.findByIdAndEstado(id, "ACTIVA").orElse(null);
        if (p == null) {
            log.warn("Publicación {} no encontrada o no está activa", id);
            return null;
        }
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