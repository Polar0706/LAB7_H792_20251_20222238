package com.tele.lab7_20222238.repository;

import com.tele.lab7_20222238.entity.Proovedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proovedor, Long> {
    boolean existsByRuc(String ruc);
}

