package com.example.perfil.service;

import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public Rol obtenerRolPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre));
    }

    public Rol obtenerRolPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }
}