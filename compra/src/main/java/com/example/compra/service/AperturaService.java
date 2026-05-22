package com.example.compra.service;

import com.example.compra.dto.AperturaRequestDTO;
import com.example.compra.dto.AperturaResponseDTO;
import com.example.compra.model.Apertura;
import com.example.compra.model.Suministro;
import com.example.compra.repository.AperturaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AperturaService {
    private final AperturaRepository aperturaRepository;
    private final SuministroService suministroService;
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public AperturaResponseDTO abrirSuministro(Long jugadorId, AperturaRequestDTO dto) {
        Suministro suministro = suministroService.obtenerEntidad(dto.getSuministroId());

        // Simular obtención de cartas según cantidadCartas
        List<String> cartasObtenidas = new ArrayList<>();
        for (int i = 0; i < suministro.getCantidadCartas(); i++) {
            String carta = generarCartaAleatoria(suministro.getProbabilidades());
            cartasObtenidas.add(carta);
        }
        String cartasJson = convertirListaAJson(cartasObtenidas);

        Apertura apertura = new Apertura();
        apertura.setJugadorId(jugadorId);
        apertura.setSuministro(suministro);
        apertura.setCartasObtenidas(cartasJson);
        apertura = aperturaRepository.save(apertura);
        log.info("Apertura {} realizada: jugador={}, suministro={}, cartas={}",
                apertura.getId(), jugadorId, suministro.getNombre(), cartasJson);
        return convertirADTO(apertura);
    }

    public List<AperturaResponseDTO> listarAperturasPorJugador(Long jugadorId) {
        return aperturaRepository.findByJugadorId(jugadorId)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private String generarCartaAleatoria(String probabilidadesJson) {
        // Implementación simple: devuelve cartas de ejemplo.
        // En un caso real, se parsearía el JSON y se elegiría según rarezas.
        String[] cartas = {"ZMB-001", "ZMB-002", "HUM-001", "HUM-002", "BEST-001"};
        return cartas[random.nextInt(cartas.length)];
    }

    private String convertirListaAJson(List<String> lista) {
        try {
            return objectMapper.writeValueAsString(lista);
        } catch (JsonProcessingException e) {
            log.error("Error convirtiendo a JSON", e);
            return "[]";
        }
    }

    private AperturaResponseDTO convertirADTO(Apertura a) {
        return new AperturaResponseDTO(
                a.getId(),
                a.getJugadorId(),
                a.getSuministro().getId(),
                a.getSuministro().getNombre(),
                a.getFecha(),
                a.getCartasObtenidas()
        );
    }
}