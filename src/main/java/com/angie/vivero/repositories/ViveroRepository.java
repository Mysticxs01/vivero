package com.angie.vivero.repositories;

import com.angie.vivero.models.ViveroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViveroRepository extends JpaRepository<ViveroModel, Long> {
    List<ViveroModel> findByFincaId(Long fincaId);
    List<ViveroModel> findByTipoCultivo(String tipoCultivo);
}
