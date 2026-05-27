package com.example.publicacion.service;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.repository.PublicacionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<PublicacionDTO> listarActivas() {
        List<Publicacion> publicaciones = publicacionRepository.findByEstado("ACTIVA");
        List<PublicacionDTO> resultado = new ArrayList<>();
        for (Publicacion pub : publicaciones) {
            PublicacionDTO dto = toDTO(pub);
            resultado.add(dto);
        }
        return resultado;
    }

    public List<PublicacionDTO> listarPorVendedor(Long vendedorId) {
        List<Publicacion> publicaciones = publicacionRepository.findByVendedorId(vendedorId);
        List<PublicacionDTO> resultado = new ArrayList<>();
        for (Publicacion pub : publicaciones) {
            PublicacionDTO dto = toDTO(pub);
            resultado.add(dto);
        }
        return resultado;
    }

    public PublicacionDTO obtenerPorId(Long id) {
        Optional<Publicacion> optional = publicacionRepository.findById(id);
        if (optional.isPresent()) {
            Publicacion publicacion = optional.get();
            return toDTO(publicacion);
        }
        return null;
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
        Optional<Publicacion> optional = publicacionRepository.findById(id);
        if (!optional.isPresent()) {
            log.info("No se encontró la publicación con ID: " + id);
            return;
        }
        Publicacion publicacion = optional.get();
        if ("VENDIDA".equals(publicacion.getEstado())) {
            log.info("No se puede eliminar la publicación " + id + " porque ya está vendida");
            return;
        }
        publicacionRepository.delete(publicacion);
        log.info("Publicación eliminada: " + id);
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

