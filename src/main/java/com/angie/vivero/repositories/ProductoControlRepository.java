package com.angie.vivero.repositories;

import com.angie.vivero.models.ProductoControlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoControlRepository extends JpaRepository<ProductoControlModel, Long> {
    Optional<ProductoControlModel> findByRegistroICA(String registroICA);
}
