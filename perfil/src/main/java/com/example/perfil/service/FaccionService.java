package com.example.perfil.service;

import com.example.perfil.model.Faccion;
import com.example.perfil.repository.FaccionRepository;
import com.example.perfil.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaccionService {

    private final FaccionRepository faccionRepository;
    private final JugadorRepository jugadorRepository;

    public List<Faccion> listar() {
        return faccionRepository.findAll();
    }
    public Faccion buscarPorId(Long id) {
        return faccionRepository.findById(id).orElse(null);
    }
    public Faccion crear(Faccion faccion) {
        // Valida que el nombre no esté en uso
        if (faccionRepository.findByNombre(faccion.getNombre()).isPresent()) {
            log.warn("Ya existe una facción con ese nombre: {}", faccion.getNombre());
            return null;
        }
        // Valida que el líder exista
        if (!jugadorRepository.existsById(faccion.getIdLider())) {
            log.warn("El líder con id {} no existe", faccion.getIdLider());
            return null;
        }
        if (faccion.getNivelInfeccion() == null) faccion.setNivelInfeccion(0);
        if (faccion.getBonoAtributo() == null) faccion.setBonoAtributo(0);

        Faccion guardada = faccionRepository.save(faccion);
        log.info("Facción creada: {} (id={})", guardada.getNombre(), guardada.getIdFaccion());
        return guardada;
    }
    public Faccion actualizar(Long id, Faccion nueva) {
        Faccion existente = faccionRepository.findById(id).orElse(null);
        if (existente == null) {
            log.warn("Facción no encontrada para actualizar, id: {}", id);
            return null;
        }
        existente.setNombre(nueva.getNombre());
        existente.setIdLider(nueva.getIdLider());
        if (nueva.getNivelInfeccion() != null) existente.setNivelInfeccion(nueva.getNivelInfeccion());
        if (nueva.getBonoAtributo() != null) existente.setBonoAtributo(nueva.getBonoAtributo());

        Faccion actualizada = faccionRepository.save(existente);
        log.info("Facción actualizada: {}", actualizada.getIdFaccion());
        return actualizada;
    }
    public boolean eliminar(Long id) {
        if (!faccionRepository.existsById(id)) {
            log.warn("Facción no encontrada para eliminar, id: {}", id);
            return false;
        }
        faccionRepository.deleteById(id);
        log.info("Facción eliminada id: {}", id);
        return true;
    }
}