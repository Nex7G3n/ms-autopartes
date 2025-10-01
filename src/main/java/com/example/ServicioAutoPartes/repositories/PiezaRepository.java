package com.example.ServicioAutoPartes.repositories;

import com.example.ServicioAutoPartes.models.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Long> {
}
