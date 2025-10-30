package com.angie.vivero.repositories;

import com.angie.vivero.models.ProductoControlFertilizanteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoControlFertilizanteRepository extends JpaRepository<ProductoControlFertilizanteModel, Long> {
}
