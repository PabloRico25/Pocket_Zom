package com.example.compra.repository;

import com.example.compra.model.Suministro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuministroRepository extends JpaRepository<Suministro, Long> {
}