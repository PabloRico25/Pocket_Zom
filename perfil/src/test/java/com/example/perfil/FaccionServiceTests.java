package com.example.perfil;

import com.example.perfil.model.Faccion;
import com.example.perfil.repository.FaccionRepository;
import com.example.perfil.repository.JugadorRepository;
import com.example.perfil.service.FaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaccionServiceTests {
    @Mock
    private FaccionRepository faccionRepository;

    @Mock
    private JugadorRepository jugadorRepository;

    @InjectMocks
    private FaccionService faccionService;

    private Faccion faccion;

    @BeforeEach
    void setUp() {
        faccion = new Faccion();
        faccion.setIdFaccion(1L);
        faccion.setNombre("Sobrevivientes");
        faccion.setIdLider(10L);
        faccion.setNivelInfeccion(0);
        faccion.setBonoAtributo(5);
    }

    @Test
    @DisplayName("Listar facciones devuelve todas las registradas")
    void listarFacciones() {
        when(faccionRepository.findAll()).thenReturn(List.of(faccion));

        List<Faccion> resultado = faccionService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sobrevivientes", resultado.get(0).getNombre());
    }
}
