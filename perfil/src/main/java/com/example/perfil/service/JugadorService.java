package com.example.perfil.service;

import com.example.perfil.dto.JugadorRequestDTO;
import com.example.perfil.dto.JugadorResponseDTO;
import com.example.perfil.dto.LoginRequestDTO;
import com.example.perfil.dto.LoginResponseDTO;
import com.example.perfil.model.Jugador;
import com.example.perfil.model.Rol;
import com.example.perfil.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JugadorService {
    private final JugadorRepository jugadorRepository;
    private final RolService rolService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<JugadorResponseDTO> listar() {
        return jugadorRepository.findAll().stream()
                .map(this::convertirADTO).collect(Collectors.toList());
    }

    public JugadorResponseDTO obtenerPorId(Long id) {
        Jugador j = jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        return convertirADTO(j);
    }

    // Obtener entidad Jugador (para uso interno en facciones)
    public Jugador obtenerEntidad(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Jugador j = jugadorRepository.findByNombreUsuario(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        if (!encoder.matches(request.getPassword(), j.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return new LoginResponseDTO(j.getId(), j.getNombreUsuario(), j.getEmail(), j.getRol().getNombre());
    }

    @Transactional
    public JugadorResponseDTO registrar(JugadorRequestDTO dto) {
        if (jugadorRepository.findByNombreUsuario(dto.getNombreUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }
        if (jugadorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }
        Jugador j = new Jugador();
        j.setNombreUsuario(dto.getNombreUsuario());
        j.setEmail(dto.getEmail());
        j.setPassword(encoder.encode(dto.getPassword()));
        // Asignar rol: si se envía rolId, usarlo; si no, usar ROLE_PLAYER (id=1 asumiendo que existe)
        Long rolId = dto.getRolId() != null ? dto.getRolId() : 1L;
        Rol rol = rolService.obtenerRolPorId(rolId);
        j.setRol(rol);
        j = jugadorRepository.save(j);
        log.info("Nuevo jugador registrado: {}", j.getNombreUsuario());
        return convertirADTO(j);
    }

    private JugadorResponseDTO convertirADTO(Jugador j) {
        return new JugadorResponseDTO(
                j.getId(),
                j.getNombreUsuario(),
                j.getEmail(),
                j.getNivel(),
                j.getRol().getNombre(),
                j.getFechaRegistro()
        );
    }
}