package com.example.mazo.repository;

import com.example.mazo.model.MazoCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MazoCartaRepository extends JpaRepository<MazoCarta, Long> {
    List<MazoCarta> findByMazoId(Long mazoId);
    Optional<MazoCarta> findByMazoIdAndCodigoCarta(Long mazoId, String codigoCarta);
    void deleteByMazoId(Long mazoId);
}