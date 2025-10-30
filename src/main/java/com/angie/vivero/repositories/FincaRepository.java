package com.angie.vivero.repositories;

import com.angie.vivero.models.FincaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FincaRepository extends JpaRepository<FincaModel, Long> {
    Optional<FincaModel> findByNumeroCatastro(String numeroCatastro);
    List<FincaModel> findByProductorId(Long productorId);
}
