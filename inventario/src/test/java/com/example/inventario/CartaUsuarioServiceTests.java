package com.example.inventario;

import com.example.inventario.cliente.CartaCliente;
import com.example.inventario.cliente.PerfilCliente;
import com.example.inventario.model.CartaUsuario;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.CartaUsuarioRepository;
import com.example.inventario.repository.InventarioRepository;
import com.example.inventario.service.CartaUsuarioService;
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
class CartaUsuarioServiceTests {

    @Mock
    private CartaUsuarioRepository cartaUsuarioRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private PerfilCliente perfilClient;

    @Mock
    private CartaCliente cartaClient;

    @InjectMocks
    private CartaUsuarioService cartaUsuarioService;

    private Inventario inventario;
    private CartaUsuario cartaUsuario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.setIdInventario(1L);
        inventario.setIdJugador(1L);

        cartaUsuario = new CartaUsuario();
        cartaUsuario.setIdCartaUsuario(1L);
        cartaUsuario.setIdInventario(1L);
        cartaUsuario.setCodigoCarta("ZMB-001");
        cartaUsuario.setCantidad(3);
    }

    @Test
    @DisplayName("Agregar carta nueva al inventario del jugador")
    void agregarCartaNueva() {
        // Given
        when(perfilClient.existeJugador(1L)).thenReturn(true);
        when(cartaClient.existeCarta("ZMB-002")).thenReturn(true);
        when(inventarioRepository.findByIdJugador(1L)).thenReturn(Optional.of(inventario));
        when(cartaUsuarioRepository.findByIdInventarioAndCodigoCarta(1L, "ZMB-002"))
                .thenReturn(Optional.empty());
        when(cartaUsuarioRepository.save(any(CartaUsuario.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        CartaUsuario resultado = cartaUsuarioService.agregar(1L, "ZMB-002", 1);

        // Then
        assertNotNull(resultado);
        assertEquals("ZMB-002", resultado.getCodigoCarta());
        assertEquals(1, resultado.getCantidad());
    }

    @Test
    @DisplayName("Agregar carta existente suma a la cantidad actual")
    void agregarCartaExistente() {
        // Given
        when(perfilClient.existeJugador(1L)).thenReturn(true);
        when(cartaClient.existeCarta("ZMB-001")).thenReturn(true);
        when(inventarioRepository.findByIdJugador(1L)).thenReturn(Optional.of(inventario));
        when(cartaUsuarioRepository.findByIdInventarioAndCodigoCarta(1L, "ZMB-001"))
                .thenReturn(Optional.of(cartaUsuario));
        when(cartaUsuarioRepository.save(any(CartaUsuario.class))).thenReturn(cartaUsuario);

        // When (tenia 3, suma 2)
        CartaUsuario resultado = cartaUsuarioService.agregar(1L, "ZMB-001", 2);

        // Then
        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidad());
    }

    @Test
    @DisplayName("No agregar carta si el jugador no existe en perfil")
    void noAgregarSiJugadorNoExiste() {
        // Given
        when(perfilClient.existeJugador(99L)).thenReturn(false);

        // When
        CartaUsuario resultado = cartaUsuarioService.agregar(99L, "ZMB-001", 1);

        // Then
        assertNull(resultado);
        verify(cartaUsuarioRepository, never()).save(any());
    }
}