package com.example.perfil.service;

import com.example.perfil.dto.FaccionDTO;
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

    public List<FaccionDTO> listar() {
        return faccionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FaccionDTO obtenerPorId(Long id) {
        Faccion faccion = faccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facción no encontrada con id: " + id));
        return toDTO(faccion);
    }

    @Transactional
    public FaccionDTO crear(FaccionDTO dto) {
        if (faccionRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una facción con el nombre: " + dto.getNombre());
        }
        Faccion faccion = new Faccion();
        faccion.setNombre(dto.getNombre());
        faccion.setLiderId(dto.getLiderId());
        faccion.setNivelInfeccion(dto.getNivelInfeccion() != null ? dto.getNivelInfeccion() : 0);
        faccion.setBonoAtributo(dto.getBonoAtributo() != null ? dto.getBonoAtributo() : 0);
        faccion = faccionRepository.save(faccion);
        log.info("Facción creada: {}", faccion.getNombre());
        return toDTO(faccion);
    }

    @Transactional
    public FaccionDTO actualizar(Long id, FaccionDTO dto) {
        Faccion faccion = faccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facción no encontrada con id: " + id));
        if (!faccion.getNombre().equals(dto.getNombre()) &&
                faccionRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una facción con el nombre: " + dto.getNombre());
        }
        faccion.setNombre(dto.getNombre());
        faccion.setLiderId(dto.getLiderId());
        faccion.setNivelInfeccion(dto.getNivelInfeccion() != null ? dto.getNivelInfeccion() : faccion.getNivelInfeccion());
        faccion.setBonoAtributo(dto.getBonoAtributo() != null ? dto.getBonoAtributo() : faccion.getBonoAtributo());
        faccion = faccionRepository.save(faccion);
        log.info("Facción actualizada: {}", faccion.getNombre());
        return toDTO(faccion);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!faccionRepository.existsById(id)) {
            throw new RuntimeException("Facción no encontrada con id: " + id);
        }
        faccionRepository.deleteById(id);
        log.info("Facción eliminada con id: {}", id);
    }

    private FaccionDTO toDTO(Faccion faccion) {
        FaccionDTO dto = new FaccionDTO();
        dto.setId(faccion.getId());
        dto.setNombre(faccion.getNombre());
        dto.setLiderId(faccion.getLiderId());
        if (faccion.getLiderId() != null) {
            try {
                Jugador lider = jugadorService.obtenerEntidad(faccion.getLiderId());
                dto.setLiderNombre(lider.getNombreUsuario());
            } catch (Exception e) {
                dto.setLiderNombre("DESCONOCIDO");
            }
        }
        dto.setNivelInfeccion(faccion.getNivelInfeccion());
        dto.setBonoAtributo(faccion.getBonoAtributo());
        return dto;
    }
}