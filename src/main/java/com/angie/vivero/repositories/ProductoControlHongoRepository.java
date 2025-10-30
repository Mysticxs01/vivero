package com.angie.vivero.repositories;

import com.angie.vivero.models.ProductoControlHongoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoControlHongoRepository extends JpaRepository<ProductoControlHongoModel, Long> {
    List<ProductoControlHongoModel> findByNombreHongo(String nombreHongo);
}
