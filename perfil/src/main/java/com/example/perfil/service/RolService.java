package com.example.perfil.service;

import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    public List<Rol> listar() {
        return rolRepository.findAll();
    }
    public Rol buscarPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }
    public Rol crear(Rol rol) {
        if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
            return null;
        }
        log.info("Rol creado: {}", rol.getNombre());
        return rolRepository.save(rol);
    }
}