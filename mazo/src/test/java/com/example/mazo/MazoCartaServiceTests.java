package com.example.mazo;

import com.example.mazo.cliente.InventarioCliente;
import com.example.mazo.model.Mazo;
import com.example.mazo.model.MazoCarta;
import com.example.mazo.repository.MazoCartaRepository;
import com.example.mazo.repository.MazoRepository;
import com.example.mazo.service.MazoCartaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MazoCartaServiceTests {

    @Mock
    private MazoCartaRepository mazoCartaRepository;

    @Mock
    private MazoRepository mazoRepository;

    @Mock
    private InventarioCliente inventarioClient;

    @InjectMocks
    private MazoCartaService mazoCartaService;

    private Mazo mazo;
    private MazoCarta mazoCarta;

    @BeforeEach
    void setUp() {
        mazo = new Mazo();
        mazo.setIdMazo(1L);
        mazo.setIdJugador(1L);
        mazo.setNombre("Mazo Zombie");

        mazoCarta = new MazoCarta();
        mazoCarta.setIdMazoCarta(1L);
        mazoCarta.setIdMazo(1L);
        mazoCarta.setCodigoCarta("ZMB-001");
        mazoCarta.setCantidad(2);
    }

    @Test
    @DisplayName("Listar cartas del mazo devuelve las registradas")
    void listarCartasDelMazo() {
        when(mazoCartaRepository.findByIdMazo(1L)).thenReturn(List.of(mazoCarta));

        List<MazoCarta> resultado = mazoCartaService.listarCartas(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ZMB-001", resultado.get(0).getCodigoCarta());
    }

    @Test
    @DisplayName("Quitar carta devuelve false si la carta no esta en el mazo")
    void quitarCartaInexistente() {
        when(mazoCartaRepository.findByIdMazoAndCodigoCarta(1L, "XXX-999")).thenReturn(Optional.empty());

        boolean resultado = mazoCartaService.quitar(1L, "XXX-999", 1);

        assertFalse(resultado);
    }
}
