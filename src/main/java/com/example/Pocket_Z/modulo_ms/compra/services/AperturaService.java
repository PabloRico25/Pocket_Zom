package com.example.Pocket_Z.modulo_ms.compra.services;

import com.example.Pocket_Z.modulo_ms.compra.model.Apertura;
import com.example.Pocket_Z.modulo_ms.compra.model.Suministro;
import com.example.Pocket_Z.modulo_ms.compra.repository.AperturaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AperturaService {
    private final AperturaRepository aperturaRepository;
    private final SuministroService suministroService;
    private final Random random = new Random();
    private final ObjectMapper mapper = new ObjectMapper();

    public Apertura abrirSuministro(Long jugadorId, Long suministroId) {
        Suministro suministro = suministroService.listar().stream()
                .filter(s -> s.getId().equals(suministroId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Suministro no encontrado"));

        // Simular obtención de cartas según probabilidades (simplificado)
        List<String> cartasObtenidas = new ArrayList<>();
        for (int i = 0; i < suministro.getCantidadCartas(); i++) {
            String carta = generarCartaAleatoria(suministro.getProbabilidades());
            cartasObtenidas.add(carta);
        }

        Apertura apertura = new Apertura();
        apertura.setJugadorId(jugadorId);
        apertura.setSuministro(suministro);
        apertura.setCartasObtenidas(convertirListaAJson(cartasObtenidas));

        return aperturaRepository.save(apertura);
    }

    private String generarCartaAleatoria(String probabilidadesJson) {
        // Implementación muy simple: devuelve códigos de ejemplo
        // En un caso real, parsearías el JSON y elegirías según rareza
        String[] cartas = {"ZMB-001", "ZMB-002", "HUM-001", "HUM-002", "BEST-001"};
        return cartas[random.nextInt(cartas.length)];
    }

    private String convertirListaAJson(List<String> lista) {
        try {
            return mapper.writeValueAsString(lista);
        } catch (Exception e) {
            return "[]";
        }
    }

    public List<Apertura> listarAperturasPorJugador(Long jugadorId) {
        return aperturaRepository.findByJugadorId(jugadorId);
    }
}