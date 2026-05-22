package com.example.publicacion.service;

import com.example.publicacion.dto.PublicacionRequestDTO;
import com.example.publicacion.dto.PublicacionResponseDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicacionService {
    private final PublicacionRepository publicacionRepository;

    public List<PublicacionResponseDTO> listarActivas() {
        return publicacionRepository.findByEstado("ACTIVA")
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<PublicacionResponseDTO> listarPorVendedor(Long vendedorId) {
        return publicacionRepository.findByVendedorId(vendedorId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public PublicacionResponseDTO obtenerPorId(Long id) {
        Publicacion p = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        return convertirADTO(p);
    }

    @Transactional
    public PublicacionResponseDTO crearPublicacion(Long vendedorId, PublicacionRequestDTO dto) {
        Publicacion p = new Publicacion();
        p.setVendedorId(vendedorId);
        p.setCodigoCarta(dto.getCodigoCarta().trim().toUpperCase());
        p.setPrecio(dto.getPrecio());
        p.setEstado("ACTIVA");
        p = publicacionRepository.save(p);
        log.info("Publicación {} creada por vendedor {}", p.getId(), vendedorId);
        return convertirADTO(p);
    }

    @Transactional
    public void eliminarPublicacion(Long id) {
        Publicacion p = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        if ("VENDIDA".equals(p.getEstado())) {
            throw new RuntimeException("No se puede eliminar una publicación vendida");
        }
        publicacionRepository.delete(p);
        log.info("Publicación {} eliminada", id);
    }

    // Método interno para marcar como vendida (usado por transacciones)
    @Transactional
    Publicacion marcarComoVendida(Long id) {
        Publicacion p = publicacionRepository.findByIdAndEstado(id, "ACTIVA")
                .orElseThrow(() -> new RuntimeException("Publicación no activa"));
        p.setEstado("VENDIDA");
        return publicacionRepository.save(p);
    }

    private PublicacionResponseDTO convertirADTO(Publicacion p) {
        return new PublicacionResponseDTO(p.getId(), p.getVendedorId(), p.getCodigoCarta(),
                p.getPrecio(), p.getEstado(), p.getFechaPublicacion());
    }
}