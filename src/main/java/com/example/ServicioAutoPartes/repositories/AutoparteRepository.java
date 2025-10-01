package com.example.ServicioAutoPartes.repositories;

import com.example.ServicioAutoPartes.models.Autoparte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutoparteRepository extends JpaRepository<Autoparte, Long> {
    Optional<Autoparte> findByCodigoProducto(String codigoProducto);
    List<Autoparte> findByModeloId(Long modeloId);
    List<Autoparte> findByPiezaId(Long piezaId);
}
