package com.example.ServicioAutoPartes.repositories;

import com.example.ServicioAutoPartes.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
