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
        return faccionRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    public FaccionDTO obtenerPorId(Long id) {
        Faccion f = faccionRepository.findById(id).orElseThrow(() -> new RuntimeException("Facción no encontrada"));
        return toDTO(f);
    }
    @Transactional
    public FaccionDTO crear(FaccionDTO dto) {
        if (faccionRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Facción ya existe");
        }
        if (dto.getLiderId() != null && !jugadorService.existeJugador(dto.getLiderId())) {
            throw new RuntimeException("El líder con id " + dto.getLiderId() + " no existe");
        }
        Faccion f = new Faccion();
        f.setNombre(dto.getNombre());
        f.setLiderId(dto.getLiderId());
        f.setNivelInfeccion(dto.getNivelInfeccion() != null ? dto.getNivelInfeccion() : 0);
        f.setBonoAtributo(dto.getBonoAtributo() != null ? dto.getBonoAtributo() : 0);
        f = faccionRepository.save(f);
        log.info("Facción creada: {}", f.getNombre());
        return toDTO(f);
    }
    @Transactional
    public FaccionDTO actualizar(Long id, FaccionDTO dto) {
        Faccion f = faccionRepository.findById(id).orElseThrow(() -> new RuntimeException("Facción no encontrada"));
        if (!f.getNombre().equals(dto.getNombre()) && faccionRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe otra facción con ese nombre");
        }
        if (dto.getLiderId() != null && !jugadorService.existeJugador(dto.getLiderId())) {
            throw new RuntimeException("El líder no existe");
        }
        f.setNombre(dto.getNombre());
        f.setLiderId(dto.getLiderId());
        f.setNivelInfeccion(dto.getNivelInfeccion() != null ? dto.getNivelInfeccion() : f.getNivelInfeccion());
        f.setBonoAtributo(dto.getBonoAtributo() != null ? dto.getBonoAtributo() : f.getBonoAtributo());
        f = faccionRepository.save(f);
        log.info("Facción actualizada: {}", f.getNombre());
        return toDTO(f);
    }
    @Transactional
    public void eliminar(Long id) {
        if (!faccionRepository.existsById(id)) {
            throw new RuntimeException("Facción no encontrada");
        }
        faccionRepository.deleteById(id);
        log.info("Facción eliminada id: {}", id);
    }
    private FaccionDTO toDTO(Faccion f) {
        FaccionDTO dto = new FaccionDTO();
        dto.setId(f.getId());
        dto.setNombre(f.getNombre());
        dto.setLiderId(f.getLiderId());
        if (f.getLiderId() != null) {
            try {
                Jugador lider = jugadorService.obtenerEntidad(f.getLiderId());
                dto.setLiderNombre(lider.getNombreUsuario());
            } catch (Exception e) {
                dto.setLiderNombre("DESCONOCIDO");
            }
        }
        dto.setNivelInfeccion(f.getNivelInfeccion());
        dto.setBonoAtributo(f.getBonoAtributo());
        return dto;
    }
}