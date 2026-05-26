package com.example.compra.repository;

import com.example.compra.model.Suministro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuministroRepository extends JpaRepository<Suministro, Long> {
}