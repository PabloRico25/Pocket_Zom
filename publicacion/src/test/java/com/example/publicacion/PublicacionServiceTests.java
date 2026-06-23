package com.example.publicacion;

import com.example.publicacion.dto.PublicacionDTO;
import com.example.publicacion.model.Publicacion;
import com.example.publicacion.repository.PublicacionRepository;
import com.example.publicacion.service.PublicacionService;
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
public class PublicacionServiceTests {
    @Mock
    private PublicacionRepository publicacionRepository;

    @InjectMocks
    private PublicacionService publicacionService;

    private Publicacion publicacion;

    @BeforeEach
    void setUp() {
        publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setVendedorId(10L);
        publicacion.setCodigoCarta("ZMB-001");
        publicacion.setPrecio(100);
        publicacion.setEstado("ACTIVA");
    }

    @Test
    @DisplayName("Listar publicaciones activas devuelve solo las activas")
    void listarActivas() {
        when(publicacionRepository.findByEstado("ACTIVA")).thenReturn(List.of(publicacion));

        List<PublicacionDTO> resultado = publicacionService.listarActivas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ACTIVA", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("Listar por vendedor devuelve sus publicaciones")
    void listarPorVendedor() {
        when(publicacionRepository.findByVendedorId(10L)).thenReturn(List.of(publicacion));

        List<PublicacionDTO> resultado = publicacionService.listarPorVendedor(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getVendedorId());
    }

    @Test
    @DisplayName("Buscar publicacion por ID existente")
    void buscarPorIdExistente() {
        when(publicacionRepository.findById(1L)).thenReturn(Optional.of(publicacion));

        PublicacionDTO resultado = publicacionService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("ZMB-001", resultado.getCodigoCarta());
    }
}
