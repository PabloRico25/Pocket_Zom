package com.example.Pocket_Z.modulo_ms.perfil.services;

import com.example.Pocket_Z.modulo_ms.perfil.model.*;
import com.example.Pocket_Z.modulo_ms.perfil.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final JugadorRepository jugadorRepository;
    private final RolRepository rolRepository;
    private final FaccionRepository faccionRepository;
    private final JugadorFaccionRepository jugadorFaccionRepository;

    // --- Jugadores ---
    public List<Jugador> listarJugadores() {
        return jugadorRepository.findAll();
    }

    public Jugador obtenerJugador(Long id) {
        return jugadorRepository.findById(id).orElse(null);
    }

    public Jugador obtenerJugadorPorNombre(String nombre) {
        return jugadorRepository.findByNombreUsuario(nombre).orElse(null);
    }

    @Transactional
    public Jugador crearJugador(Jugador jugador, String rolNombre) {
        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        jugador.setRol(rol);
        // Aquí luego se cifrará la contraseña con BCrypt
        return jugadorRepository.save(jugador);
    }

    @Transactional
    public void eliminarJugador(Long id) {
        jugadorRepository.deleteById(id);
    }

    // --- Roles (para inicializar) ---
    @Transactional
    public Rol crearRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        return rolRepository.save(rol);
    }

    // --- Facciones ---
    public List<Faccion> listarFacciones() {
        return faccionRepository.findAll();
    }

    @Transactional
    public Faccion crearFaccion(String nombre, Long liderId) {
        Jugador lider = jugadorRepository.findById(liderId)
                .orElseThrow(() -> new RuntimeException("Líder no encontrado"));
        Faccion faccion = new Faccion();
        faccion.setNombre(nombre);
        faccion.setLider(lider);
        return faccionRepository.save(faccion);
    }

    // --- Membresía a facción ---
    @Transactional
    public JugadorFaccion unirAFaccion(Long jugadorId, Long faccionId) {
        Jugador jugador = jugadorRepository.findById(jugadorId).orElseThrow();
        Faccion faccion = faccionRepository.findById(faccionId).orElseThrow();
        JugadorFaccion jf = new JugadorFaccion();
        jf.setJugador(jugador);
        jf.setFaccion(faccion);
        return jugadorFaccionRepository.save(jf);
    }

    public List<JugadorFaccion> obtenerFaccionesDeJugador(Long jugadorId) {
        return jugadorFaccionRepository.findByJugadorId(jugadorId);
    }
}