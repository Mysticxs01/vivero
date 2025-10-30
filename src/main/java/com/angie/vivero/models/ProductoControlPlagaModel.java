package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "productos_control_plaga")
public class ProductoControlPlagaModel extends ProductoControlModel {
    
    @NotNull(message = "El periodo de carencia es obligatorio")
    @Column(nullable = false)
    private Integer periodoCarencia; // En días

    public ProductoControlPlagaModel() {
    }

    public ProductoControlPlagaModel(String registroICA, String nombreProducto, Integer frecuenciaAplicacion, 
                                    BigDecimal valor, Integer periodoCarencia) {
        super(registroICA, nombreProducto, frecuenciaAplicacion, valor);
        this.periodoCarencia = periodoCarencia;
    }

    public Integer getPeriodoCarencia() {
        return periodoCarencia;
    }

    public void setPeriodoCarencia(Integer periodoCarencia) {
        this.periodoCarencia = periodoCarencia;
    }
}
