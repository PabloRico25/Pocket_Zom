package com.example.logros.repository;

import com.example.logros.model.Logro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogroRepository extends JpaRepository<Logro, String> {
    List<Logro> findByCondicionTipo(String condicionTipo);
}