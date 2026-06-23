package com.example.logros;

import com.example.logros.client.BilleteraClient;
import com.example.logros.dto.LogroDTO;
import com.example.logros.dto.LogroJugadorDTO;
import com.example.logros.model.Logro;
import com.example.logros.model.LogroJugador;
import com.example.logros.repository.LogroJugadorRepository;
import com.example.logros.service.LogroJugadorService;
import com.example.logros.service.LogroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogroJugadorServiceTests {

    @Mock
    private LogroJugadorRepository logroJugadorRepository;

    @Mock
    private LogroService logroService;

    @Mock
    private BilleteraClient billeteraClient;

    @InjectMocks
    private LogroJugadorService logroJugadorService;

    private Logro logro;
    private LogroJugador logroJugador;

    @BeforeEach
    void setUp() {
        logro = new Logro();
        logro.setIdLogro("LOG-001");
        logro.setNombre("Primera Victoria");
        logro.setCondicionTipo("VICTORIAS");
        logro.setCondicionValor(1);
        logro.setRecompensaMonedas(50);
        logro.setRecompensaExp(20);

        logroJugador = new LogroJugador();
        logroJugador.setId(1L);
        logroJugador.setJugadorId(10L);
        logroJugador.setIdLogro("LOG-001");
    }

    @Test
    @DisplayName("Desbloquear logro guarda el registro y otorga recompensa")
    void desbloquearLogroExitoso() {
        // Given
        when(logroJugadorRepository.findByJugadorIdAndIdLogro(10L, "LOG-001"))
                .thenReturn(Optional.empty());
        when(logroService.obtenerEntidad("LOG-001")).thenReturn(logro);
        when(logroJugadorRepository.save(any(LogroJugador.class))).thenReturn(logroJugador);

        // When
        LogroJugadorDTO resultado = logroJugadorService.desbloquear(10L, "LOG-001");

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getJugadorId());
        verify(billeteraClient, times(1))
                .registrarMovimiento(eq(10L), eq("INGRESO"), eq(50), any(String.class));
    }

    @Test
    @DisplayName("No desbloquear logro si ya esta desbloqueado")
    void noDesbloquearSiYaLoTiene() {
        // Given
        when(logroJugadorRepository.findByJugadorIdAndIdLogro(10L, "LOG-001"))
                .thenReturn(Optional.of(logroJugador));

        // When
        LogroJugadorDTO resultado = logroJugadorService.desbloquear(10L, "LOG-001");

        // Then
        assertNull(resultado);
        verify(logroJugadorRepository, never()).save(any());
        verify(billeteraClient, never()).registrarMovimiento(any(), any(), any(), any());
    }

    @Test
    @DisplayName("Verificar y desbloquear logros cuando se cumple la condicion")
    void verificarYDesbloquearCumpleCondicion() {
        // Given (jugador con 5 victorias, logro pide 1)
        LogroDTO logroDTO = new LogroDTO();
        logroDTO.setIdLogro("LOG-001");

        when(logroService.listarPorTipo("VICTORIAS")).thenReturn(List.of(logroDTO));
        when(logroService.obtenerEntidad("LOG-001")).thenReturn(logro);
        when(logroJugadorRepository.findByJugadorIdAndIdLogro(10L, "LOG-001"))
                .thenReturn(Optional.empty());
        when(logroJugadorRepository.save(any(LogroJugador.class))).thenReturn(logroJugador);

        // When
        List<LogroJugadorDTO> resultado = logroJugadorService.verificarYDesbloquear(10L, "VICTORIAS", 5);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
}