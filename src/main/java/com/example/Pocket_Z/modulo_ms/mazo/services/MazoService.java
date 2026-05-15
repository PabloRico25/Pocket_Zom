package com.example.Pocket_Z.modulo_ms.mazo.services;

import com.example.Pocket_Z.modulo_ms.mazo.model.Mazo;
import com.example.Pocket_Z.modulo_ms.mazo.repository.MazoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MazoService {
    private final MazoRepository mazoRepository;

    public List<Mazo> listarPorJugador(Long jugadorId) {
        return mazoRepository.findByJugadorId(jugadorId);
    }

    public Mazo guardar(Mazo mazo) {
        // Si el nuevo mazo es activo, desactivar los otros del mismo jugador
        if (mazo.getEsActivo() != null && mazo.getEsActivo()) {
            List<Mazo> activos = mazoRepository.findByJugadorId(mazo.getJugadorId());
            activos.forEach(a -> {
                a.setEsActivo(false);
                mazoRepository.save(a);
            });
        }
        return mazoRepository.save(mazo);
    }

    public void eliminar(Long id) {
        mazoRepository.deleteById(id);
    }

    public Mazo obtenerMazoActivo(Long jugadorId) {
        return mazoRepository.findByJugadorIdAndEsActivoTrue(jugadorId).orElse(null);
    }
}
