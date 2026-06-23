package com.example.rango;

import com.example.rango.dto.ClasificacionDTO;
import com.example.rango.model.Clasificacion;
import com.example.rango.repository.ClasificacionRepository;
import com.example.rango.service.ClasificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClasificacionServiceTests {
    @Mock
    private ClasificacionRepository clasificacionRepository;

    @InjectMocks
    private ClasificacionService clasificacionService;

    private Clasificacion clasificacion;

    @BeforeEach
    void setUp() {
        clasificacion = new Clasificacion();
        clasificacion.setId(1L);
        clasificacion.setJugadorId(1L);
        clasificacion.setPuntosElo(1000);
        clasificacion.setVictorias(0);
        clasificacion.setDerrotas(0);
        clasificacion.setRangoActual("Bronce");
    }

    @Test
    @DisplayName("Crear clasificacion para jugador sin clasificacion previa")
    void crearClasificacionExitosamente() {
        when(clasificacionRepository.findByJugadorId(1L)).thenReturn(Optional.empty());
        when(clasificacionRepository.save(any(Clasificacion.class))).thenReturn(clasificacion);

        ClasificacionDTO resultado = clasificacionService.crearClasificacion(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getJugadorId());
        assertEquals(1000, resultado.getPuntosElo());
        assertEquals("Bronce", resultado.getRangoActual());
    }

    @Test
    @DisplayName("No crear clasificacion si el jugador ya tiene una")
    void noCrearSiYaExiste() {
        when(clasificacionRepository.findByJugadorId(1L)).thenReturn(Optional.of(clasificacion));

        ClasificacionDTO resultado = clasificacionService.crearClasificacion(1L);

        assertNull(resultado);
        verify(clasificacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Obtener clasificacion por jugador existente")
    void obtenerPorJugadorExistente() {
        when(clasificacionRepository.findByJugadorId(1L)).thenReturn(Optional.of(clasificacion));

        ClasificacionDTO resultado = clasificacionService.obtenerPorJugador(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getJugadorId());
        assertEquals("Bronce", resultado.getRangoActual());
    }

}
