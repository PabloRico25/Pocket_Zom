package com.example.perfil.service;

import com.example.perfil.dto.RolDTO;
import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<RolDTO> listar() {
        return rolRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RolDTO obtenerPorId(Long id) {
        Rol rol = rolRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return toDTO(rol);
    }

    @Transactional
    public RolDTO crear(RolDTO dto) {
        if (rolRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Rol ya existe");
        }
        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        rol = rolRepository.save(rol);
        log.info("Rol creado: {}", rol.getNombre());
        return toDTO(rol);
    }

    private RolDTO toDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        return dto;
    }
}