package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "productos_control_fertilizante")
public class ProductoControlFertilizanteModel extends ProductoControlModel {
    
    @NotNull(message = "La fecha de última aplicación es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaUltimaAplicacion;

    public ProductoControlFertilizanteModel() {
    }

    public ProductoControlFertilizanteModel(String registroICA, String nombreProducto, Integer frecuenciaAplicacion, 
                                           BigDecimal valor, LocalDate fechaUltimaAplicacion) {
        super(registroICA, nombreProducto, frecuenciaAplicacion, valor);
        this.fechaUltimaAplicacion = fechaUltimaAplicacion;
    }

    public LocalDate getFechaUltimaAplicacion() {
        return fechaUltimaAplicacion;
    }

    public void setFechaUltimaAplicacion(LocalDate fechaUltimaAplicacion) {
        this.fechaUltimaAplicacion = fechaUltimaAplicacion;
    }
}
