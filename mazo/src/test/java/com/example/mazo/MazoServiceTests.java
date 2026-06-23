package com.example.mazo;

import com.example.mazo.cliente.PerfilCliente;
import com.example.mazo.model.Mazo;
import com.example.mazo.repository.MazoRepository;
import com.example.mazo.service.MazoService;
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
class MazoServiceTests {

    @Mock
    private MazoRepository mazoRepository;

    @Mock
    private PerfilCliente perfilClient;

    @InjectMocks
    private MazoService mazoService;

    private Mazo mazo;

    @BeforeEach
    void setUp() {
        mazo = new Mazo();
        mazo.setIdMazo(1L);
        mazo.setIdJugador(1L);
        mazo.setNombre("Mazo Zombie");
        mazo.setEsActivo(false);
    }

    @Test
    @DisplayName("Listar mazos por jugador existente")
    void listarMazosPorJugador() {
        when(mazoRepository.findByIdJugador(1L)).thenReturn(List.of(mazo));

        List<Mazo> resultado = mazoService.listarPorJugador(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Mazo Zombie", resultado.get(0).getNombre());
    }
}
