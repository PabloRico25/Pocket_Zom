package com.example.cartacatalogo;

import com.example.cartacatalogo.model.Carta;
import com.example.cartacatalogo.repository.CartaRepository;
import com.example.cartacatalogo.service.CartaService;
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
class CartaServiceTests {

    @Mock
    private CartaRepository cartaRepository;

    @InjectMocks
    private CartaService cartaService;

    private Carta carta;

    @BeforeEach
    void setUp() {
        carta = new Carta();
        carta.setId(1L);
        carta.setCodigo("ZMB-001");
        carta.setNombre("Zombie Inicial");
        carta.setRaza("No-Muerto");
        carta.setAtaque(10);
        carta.setDefensa(5);
        carta.setCoste(2);
        carta.setHabilidad("Devorar");
        carta.setActiva(true);
    }

    @Test
    @DisplayName("Crear carta con codigo unico la guarda correctamente")
    void crearCartaExitosamente() {
        // Given
        when(cartaRepository.existsByCodigo("ZMB-001")).thenReturn(false);
        when(cartaRepository.save(any(Carta.class))).thenReturn(carta);

        // When
        Carta resultado = cartaService.crear(carta);

        // Then
        assertNotNull(resultado);
        assertEquals("ZMB-001", resultado.getCodigo());
        verify(cartaRepository, times(1)).save(any(Carta.class));
    }

    @Test
    @DisplayName("No crear carta si el codigo ya existe")
    void noCrearSiCodigoDuplicado() {
        // Given
        when(cartaRepository.existsByCodigo("ZMB-001")).thenReturn(true);

        // When
        Carta resultado = cartaService.crear(carta);

        // Then
        assertNull(resultado);
        verify(cartaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Eliminar carta existente devuelve true")
    void eliminarCartaExistente() {
        // Given
        when(cartaRepository.existsById(1L)).thenReturn(true);

        // When
        boolean resultado = cartaService.eliminar(1L);

        // Then
        assertTrue(resultado);
        verify(cartaRepository, times(1)).deleteById(1L);
    }
}