package com.example.perfil.service;

import com.example.perfil.dto.JugadorDTO;
import com.example.perfil.model.Jugador;
import com.example.perfil.model.Rol;
import com.example.perfil.repository.JugadorRepository;
import com.example.perfil.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JugadorService {
    private final JugadorRepository jugadorRepository;
    private final RolRepository rolRepository;

    @Transactional
    public JugadorDTO registrar(JugadorDTO dto) {
        if (jugadorRepository.findByNombreUsuario(dto.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("Nombre de usuario ya existe: " + dto.getNombreUsuario());
        }
        if (jugadorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya registrado: " + dto.getEmail());
        }
        Long rolId = dto.getRolId() != null ? dto.getRolId() : 1L; // por defecto ROLE_PLAYER
        Jugador jugador = new Jugador();
        jugador.setNombreUsuario(dto.getNombreUsuario());
        jugador.setEmail(dto.getEmail());
        jugador.setPassword(dto.getPassword()); // texto plano (solo académico)
        jugador.setNivel(1);
        jugador.setRolId(rolId);
        jugador.setFechaRegistro(LocalDateTime.now());
        jugador = jugadorRepository.save(jugador);
        log.info("Jugador registrado: {}", jugador.getNombreUsuario());
        return toDTO(jugador);
    }

    public JugadorDTO login(String username, String rawPassword) {
        Jugador jugador = jugadorRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (!jugador.getPassword().equals(rawPassword)) {
            throw new RuntimeException("Credenciales inválidas");
        }
        log.info("Jugador logueado: {}", username);
        return toDTO(jugador);
    }

    public boolean existeJugador(Long id) {
        return jugadorRepository.existsById(id);
    }

    // Método interno para obtener entidad (usado por FaccionService)
    public Jugador obtenerEntidad(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + id));
    }

    private JugadorDTO toDTO(Jugador jugador) {
        JugadorDTO dto = new JugadorDTO();
        dto.setId(jugador.getId());
        dto.setNombreUsuario(jugador.getNombreUsuario());
        dto.setEmail(jugador.getEmail());
        dto.setNivel(jugador.getNivel());
        dto.setRolId(jugador.getRolId());
        dto.setFechaRegistro(jugador.getFechaRegistro());
        // Cargar nombre del rol
        rolRepository.findById(jugador.getRolId()).ifPresent(rol -> dto.setRolNombre(rol.getNombre()));
        return dto;
    }
}