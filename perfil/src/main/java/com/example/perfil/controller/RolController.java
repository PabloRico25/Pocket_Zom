package com.example.perfil.controller;

import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
    private final RolRepository rolRepository;

    // Listar todos los roles
    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> roles = rolRepository.findAll();
        return roles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(roles);
    }

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<Rol> crear(@Valid @RequestBody Rol rol) {
        if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Rol nuevo = rolRepository.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Obtener rol por ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable Long id) {
        return rolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}