package com.example.Pocket_Z.modulo_ms.publicacion.services;

import com.example.Pocket_Z.modulo_ms.publicacion.model.Publicacion;
import com.example.Pocket_Z.modulo_ms.publicacion.model.Transaccion;
import com.example.Pocket_Z.modulo_ms.publicacion.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final PublicacionService publicacionService;

    public Transaccion registrarCompra(Long publicacionId, Long compradorId) {
        Publicacion pub = publicacionService.listarActivas().stream()
                .filter(p -> p.getId().equals(publicacionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Publicación no activa"));

        pub.setEstado("VENDIDA");
        publicacionService.marcarComoVendida(publicacionId);

        Transaccion trans = new Transaccion();
        trans.setPublicacion(pub);
        trans.setCompradorId(compradorId);
        return transaccionRepository.save(trans);
    }

    public List<Transaccion> listarComprasPorComprador(Long compradorId) {
        return transaccionRepository.findByCompradorId(compradorId);
    }

    public List<Transaccion> listarVentasPorVendedor(Long vendedorId) {
        return transaccionRepository.findByPublicacion_VendedorId(vendedorId);
    }
}