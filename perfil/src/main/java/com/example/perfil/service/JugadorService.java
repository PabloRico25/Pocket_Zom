package com.example.perfil.service;

import com.example.perfil.model.Jugador;
import com.example.perfil.model.Rol;
import com.example.perfil.repository.JugadorRepository;
import com.example.perfil.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final RolRepository rolRepository;
    public List<Jugador> listarTodos() {
        return jugadorRepository.findAll();
    }
    public Jugador buscarPorId(Long id) {
        return jugadorRepository.findById(id).orElse(null);
    }
    public boolean existeJugador(Long id) {
        return jugadorRepository.existsById(id);
    }
    public Jugador registrar(Jugador jugador) {
        if (jugadorRepository.findByNombreUsuario(jugador.getNombreUsuario()).isPresent()) {
            log.warn("Nombre de usuario ya existe: {}", jugador.getNombreUsuario());
            return null;
        }
        if (jugadorRepository.findByEmail(jugador.getEmail()).isPresent()) {
            log.warn("Email ya registrado: {}", jugador.getEmail());
            return null;
        }
        Rol rol = rolRepository.findByNombre("ROLE_PLAYER").orElse(null);
        if (rol == null) {
            log.warn("No se encontró el rol ROLE_PLAYER");
            return null;
        }
        jugador.setIdRol(rol.getIdRol());
        jugador.setNivel(1);
        Jugador guardado = jugadorRepository.save(jugador);
        log.info("Jugador registrado: {} (id={})", guardado.getNombreUsuario(), guardado.getIdJugador());
        return guardado;
    }
    public Jugador login(String nombreUsuario, String password) {
        Jugador jugador = jugadorRepository.findByNombreUsuario(nombreUsuario).orElse(null);
        if (jugador == null || !jugador.getPassword().equals(password)) {
            log.warn("Credenciales inválidas para: {}", nombreUsuario);
            return null;
        }
        log.info("Jugador logueado: {}", nombreUsuario);
        return jugador;
    }
    public Jugador actualizar(Long id, Jugador nuevo) {
        Jugador existente = jugadorRepository.findById(id).orElse(null);
        if (existente == null) {
            log.warn("Jugador no encontrado para actualizar, id: {}", id);
            return null;
        }
        existente.setNombreUsuario(nuevo.getNombreUsuario());
        existente.setEmail(nuevo.getEmail());
        existente.setNivel(nuevo.getNivel());
        Jugador actualizado = jugadorRepository.save(existente);
        log.info("Jugador actualizado: {}", actualizado.getIdJugador());
        return actualizado;
    }
    public boolean eliminar(Long id) {
        if (!jugadorRepository.existsById(id)) {
            log.warn("Jugador no encontrado para eliminar, id: {}", id);
            return false;
        }
        jugadorRepository.deleteById(id);
        log.info("Jugador eliminado id: {}", id);
        return true;
    }
}