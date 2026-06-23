package com.example.partida;

import com.example.partida.client.BilleteraCliente;
import com.example.partida.client.RangoCliente;
import com.example.partida.dto.PartidaDTO;
import com.example.partida.model.Partida;
import com.example.partida.repository.PartidaRepository;
import com.example.partida.service.PartidaService;
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
public class PartidaServiceTests {
    @Mock
    private PartidaRepository partidaRepository;

    @Mock
    private BilleteraCliente billeteraCliente;

    @Mock
    private RangoCliente rangoCliente;

    @InjectMocks
    private PartidaService partidaService;

    private Partida partida;

    @BeforeEach
    void setUp() {
        partida = new Partida();
        partida.setId(1L);
        partida.setJugador1Id(10L);
        partida.setJugador2Id(20L);
        partida.setMazoJ1Id(100L);
        partida.setMazoJ2Id(200L);
        partida.setEstado("EN_CURSO");
    }

    @Test
    @DisplayName("Listar todas las partidas devuelve los DTOs correctamente")
    void listarTodas() {
        // Given
        when(partidaRepository.findAll()).thenReturn(List.of(partida));

        // When
        List<PartidaDTO> resultado = partidaService.listarTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getJugador1Id());
        assertEquals("EN_CURSO", resultado.get(0).getEstado());
    }

    @Test
    @DisplayName("Listar partidas por jugador devuelve sus partidas")
    void listarPorJugador() {
        when(partidaRepository.findByJugador1IdOrJugador2Id(10L, 10L)).thenReturn(List.of(partida));

        List<PartidaDTO> resultado = partidaService.listarPorJugador(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getJugador1Id());
    }

    @Test
    @DisplayName("Obtener partida por ID existente")
    void obtenerPorIdExistente() {
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partida));

        PartidaDTO resultado = partidaService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

}
