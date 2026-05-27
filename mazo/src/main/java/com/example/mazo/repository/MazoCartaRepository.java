package com.example.mazo.repository;

import com.example.mazo.model.MazoCarta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MazoCartaRepository extends JpaRepository<MazoCarta, Long> {
    List<MazoCarta> findByIdMazo(Long idMazo);
    Optional<MazoCarta> findByIdMazoAndCodigoCarta(Long idMazo, String codigoCarta);
    void deleteByIdMazo(Long idMazo);
}