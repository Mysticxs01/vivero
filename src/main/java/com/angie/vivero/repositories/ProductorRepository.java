package com.angie.vivero.repositories;

import com.angie.vivero.models.ProductorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductorRepository extends JpaRepository<ProductorModel, Long> {
    Optional<ProductorModel> findByDocumento(String documento);
}
