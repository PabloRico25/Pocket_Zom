package com.example.Pocket_Z.modulo_ms.mazo.repository;

import com.example.Pocket_Z.modulo_ms.mazo.model.MazoCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MazoCartaRepository extends JpaRepository<MazoCarta, Long> {
    List<MazoCarta> findByMazoId(Long mazoId);
    void deleteByMazoId(Long mazoId);
}