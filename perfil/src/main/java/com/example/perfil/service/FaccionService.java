package com.example.perfil.service;

import com.example.perfil.dto.FaccionRequestDTO;
import com.example.perfil.dto.FaccionResponseDTO;
import com.example.perfil.model.Faccion;
import com.example.perfil.model.Jugador;
import com.example.perfil.repository.FaccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaccionService {
    private final FaccionRepository faccionRepository;
    private final JugadorService jugadorService;

    public List<FaccionResponseDTO> listar() {
        return faccionRepository.findAll().stream()
                .map(this::convertirADTO).collect(Collectors.toList());
    }

    public FaccionResponseDTO obtenerPorId(Long id) {
        Faccion f = faccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facción no encontrada"));
        return convertirADTO(f);
    }

    // Obtener entidad Faccion (para uso interno)
    public Faccion obtenerEntidad(Long id) {
        return faccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facción no encontrada"));
    }

    @Transactional
    public FaccionResponseDTO crear(FaccionRequestDTO dto) {
        if (faccionRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una facción con ese nombre");
        }
        Faccion f = new Faccion();
        f.setNombre(dto.getNombre());
        if (dto.getLiderId() != null) {
            Jugador lider = jugadorService.obtenerEntidad(dto.getLiderId());
            f.setLider(lider);
        }
        f.setNivelInfeccion(dto.getNivelInfeccion() != null ? dto.getNivelInfeccion() : 0);
        f.setBonoAtributo(dto.getBonoAtributo() != null ? dto.getBonoAtributo() : 0);
        f = faccionRepository.save(f);
        log.info("Facción creada: {}", f.getNombre());
        return convertirADTO(f);
    }

    @Transactional
    public FaccionResponseDTO actualizar(Long id, FaccionRequestDTO dto) {
        Faccion f = faccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facción no encontrada"));
        if (dto.getNombre() != null && !dto.getNombre().equals(f.getNombre())) {
            if (faccionRepository.findByNombre(dto.getNombre()).isPresent()) {
                throw new RuntimeException("Ya existe una facción con ese nombre");
            }
            f.setNombre(dto.getNombre());
        }
        if (dto.getLiderId() != null) {
            Jugador lider = jugadorService.obtenerEntidad(dto.getLiderId());
            f.setLider(lider);
        }
        if (dto.getNivelInfeccion() != null) f.setNivelInfeccion(dto.getNivelInfeccion());
        if (dto.getBonoAtributo() != null) f.setBonoAtributo(dto.getBonoAtributo());
        f = faccionRepository.save(f);
        log.info("Facción actualizada: {}", f.getNombre());
        return convertirADTO(f);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!faccionRepository.existsById(id))
            throw new RuntimeException("Facción no encontrada");
        faccionRepository.deleteById(id);
        log.info("Facción {} eliminada", id);
    }

    private FaccionResponseDTO convertirADTO(Faccion f) {
        Long liderId = f.getLider() != null ? f.getLider().getId() : null;
        String liderNombre = f.getLider() != null ? f.getLider().getNombreUsuario() : null;
        return new FaccionResponseDTO(
                f.getId(),
                f.getNombre(),
                liderId,
                liderNombre,
                f.getNivelInfeccion(),
                f.getBonoAtributo()
        );
    }
}