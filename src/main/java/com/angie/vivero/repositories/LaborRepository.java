package com.angie.vivero.repositories;

import com.angie.vivero.models.LaborModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LaborRepository extends JpaRepository<LaborModel, Long> {
    List<LaborModel> findByViveroId(Long viveroId);
    List<LaborModel> findByFecha(LocalDate fecha);
    List<LaborModel> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<LaborModel> findByProductoControlId(Long productoControlId);
}
