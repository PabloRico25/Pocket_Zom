package com.example.compra;

import com.example.compra.cliente.BilleteraCliente;
import com.example.compra.cliente.InventarioCliente;
import com.example.compra.dto.AbrirSobreDTO;
import com.example.compra.dto.AperturaDTO;
import com.example.compra.dto.MovimientoDTO;
import com.example.compra.model.Apertura;
import com.example.compra.model.Suministro;
import com.example.compra.repository.AperturaRepository;
import com.example.compra.service.AperturaService;
import com.example.compra.service.SuministroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AperturaServiceTests {

    @Mock
    private AperturaRepository aperturaRepository;

    @Mock
    private SuministroService suministroService;

    @Mock
    private BilleteraCliente billeteraCliente;

    @Mock
    private InventarioCliente inventarioCliente;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AperturaService aperturaService;

    private Suministro suministro;
    private Apertura apertura;

    @BeforeEach
    void setUp() {
        suministro = new Suministro();
        suministro.setId(1L);
        suministro.setNombre("Sobre Basico");
        suministro.setCosto(50);
        suministro.setCantidadCartas(3);

        apertura = new Apertura();
        apertura.setId(1L);
        apertura.setJugadorId(10L);
        apertura.setSuministroId(1L);
        apertura.setCartasObtenidas("[\"ZMB-001\",\"HUM-002\",\"ZMB-002\"]");
    }

    @Test
    @DisplayName("Abrir sobre exitoso descuenta costo y agrega cartas al inventario")
    void abrirSobreExitoso() throws Exception {
        // Given
        AbrirSobreDTO dto = new AbrirSobreDTO();
        dto.setSuministroId(1L);

        when(suministroService.obtenerEntidad(1L)).thenReturn(suministro);
        when(objectMapper.writeValueAsString(any())).thenReturn("[\"ZMB-001\",\"HUM-002\",\"ZMB-002\"]");
        when(aperturaRepository.save(any(Apertura.class))).thenReturn(apertura);

        // When
        AperturaDTO resultado = aperturaService.abrir(10L, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getJugadorId());
        verify(billeteraCliente, times(1)).registrarMovimiento(eq(10L), any(MovimientoDTO.class));
        verify(inventarioCliente, times(3)).agregarCarta(eq(10L), anyString(), anyInt());
    }

    @Test
    @DisplayName("No abrir sobre si el suministro no existe")
    void noAbrirSiSuministroNoExiste() {
        // Given
        AbrirSobreDTO dto = new AbrirSobreDTO();
        dto.setSuministroId(99L);
        when(suministroService.obtenerEntidad(99L)).thenReturn(null);

        // When
        AperturaDTO resultado = aperturaService.abrir(10L, dto);

        // Then
        assertNull(resultado);
        verify(billeteraCliente, never()).registrarMovimiento(any(), any());
        verify(aperturaRepository, never()).save(any());
    }

    @Test
    @DisplayName("No abrir sobre si falla el descuento en billetera")
    void noAbrirSiFallaDescuento() {
        // Given
        AbrirSobreDTO dto = new AbrirSobreDTO();
        dto.setSuministroId(1L);

        when(suministroService.obtenerEntidad(1L)).thenReturn(suministro);
        doThrow(new RuntimeException("Saldo insuficiente"))
                .when(billeteraCliente).registrarMovimiento(eq(10L), any(MovimientoDTO.class));

        // When
        AperturaDTO resultado = aperturaService.abrir(10L, dto);

        // Then
        assertNull(resultado);
        verify(aperturaRepository, never()).save(any());
        verify(inventarioCliente, never()).agregarCarta(any(), any(), any());
    }
}