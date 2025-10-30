package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "productos_control_hongo")
public class ProductoControlHongoModel extends ProductoControlModel {
    
    @NotNull(message = "El periodo de carencia es obligatorio")
    @Column(nullable = false)
    private Integer periodoCarencia; // En días
    
    @NotBlank(message = "El nombre del hongo es obligatorio")
    @Column(nullable = false)
    private String nombreHongo;

    public ProductoControlHongoModel() {
    }

    public ProductoControlHongoModel(String registroICA, String nombreProducto, Integer frecuenciaAplicacion, 
                                    BigDecimal valor, Integer periodoCarencia, String nombreHongo) {
        super(registroICA, nombreProducto, frecuenciaAplicacion, valor);
        this.periodoCarencia = periodoCarencia;
        this.nombreHongo = nombreHongo;
    }

    public Integer getPeriodoCarencia() {
        return periodoCarencia;
    }

    public void setPeriodoCarencia(Integer periodoCarencia) {
        this.periodoCarencia = periodoCarencia;
    }

    public String getNombreHongo() {
        return nombreHongo;
    }

    public void setNombreHongo(String nombreHongo) {
        this.nombreHongo = nombreHongo;
    }
}
