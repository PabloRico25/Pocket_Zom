package com.example.billetera;

import com.example.billetera.cliente.PerfilCliente;
import com.example.billetera.model.Cartera;
import com.example.billetera.model.Movimiento;
import com.example.billetera.repository.MovimientoRepository;
import com.example.billetera.service.CarteraService;
import com.example.billetera.service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTests {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CarteraService carteraService;

    @Mock
    private PerfilCliente perfilClient;

    @InjectMocks
    private MovimientoService movimientoService;

    private Cartera cartera;
    private Movimiento movimiento;

    @BeforeEach
    void setUp() {
        cartera = new Cartera();
        cartera.setIdCartera(1L);
        cartera.setIdJugador(1L);
        cartera.setSaldo(500);

        movimiento = new Movimiento();
        movimiento.setIdTransaccion(1L);
        movimiento.setIdCartera(1L);
        movimiento.setTipo("INGRESO");
        movimiento.setMonto(100);
        movimiento.setConcepto("Recarga inicial");
    }

    @Test
    @DisplayName("Registrar movimiento de INGRESO suma saldo correctamente")
    void registrarIngresoExitoso() {
        // Given
        when(perfilClient.existeJugador(1L)).thenReturn(true);
        when(carteraService.buscarPorJugador(1L)).thenReturn(cartera);
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);

        // When
        Movimiento resultado = movimientoService.registrar(1L, "INGRESO", 100, "Recarga inicial");

        // Then
        assertNotNull(resultado);
        assertEquals(600, cartera.getSaldo()); // 500 + 100
        verify(carteraService, times(1)).guardar(cartera);
    }

    @Test
    @DisplayName("Registrar movimiento de EGRESO resta saldo correctamente")
    void registrarEgresoExitoso() {
        // Given
        when(perfilClient.existeJugador(1L)).thenReturn(true);
        when(carteraService.buscarPorJugador(1L)).thenReturn(cartera);
        when(movimientoRepository.save(any(Movimiento.class))).thenReturn(movimiento);

        // When
        Movimiento resultado = movimientoService.registrar(1L, "EGRESO", 200, "Compra de sobre");

        // Then
        assertNotNull(resultado);
        assertEquals(300, cartera.getSaldo()); // 500 - 200
        verify(carteraService, times(1)).guardar(cartera);
    }

    @Test
    @DisplayName("No registrar EGRESO si el saldo resultante seria negativo")
    void noRegistrarEgresoSiSaldoNegativo() {
        // Given
        when(perfilClient.existeJugador(1L)).thenReturn(true);
        when(carteraService.buscarPorJugador(1L)).thenReturn(cartera);

        // When (intenta egresar 600 cuando solo hay 500)
        Movimiento resultado = movimientoService.registrar(1L, "EGRESO", 600, "Compra cara");

        // Then
        assertNull(resultado);
        assertEquals(500, cartera.getSaldo()); // saldo no cambia
        verify(movimientoRepository, never()).save(any());
    }
}