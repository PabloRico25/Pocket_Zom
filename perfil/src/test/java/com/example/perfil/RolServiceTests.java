package com.example.perfil;

import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import com.example.perfil.service.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RolServiceTests {
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol();
        rol.setIdRol(1L);
        rol.setNombre("ROLE_PLAYER");
    }

    @Test
    @DisplayName("Listar roles devuelve todos los registrados")
    void listarRoles() {
        when(rolRepository.findAll()).thenReturn(List.of(rol));

        List<Rol> resultado = rolService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ROLE_PLAYER", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Buscar rol por ID existente")
    void buscarPorIdExistente() {
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        Rol resultado = rolService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdRol());
    }
}
