package com.angie.vivero.repositories;

import com.angie.vivero.models.ProductoControlPlagaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoControlPlagaRepository extends JpaRepository<ProductoControlPlagaModel, Long> {
}
