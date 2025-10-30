package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "productos_control")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ProductoControlModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El registro ICA es obligatorio")
    @Column(nullable = false)
    private String registroICA;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Column(nullable = false)
    private String nombreProducto;
    
    @NotNull(message = "La frecuencia de aplicación es obligatoria")
    @Column(nullable = false)
    private Integer frecuenciaAplicacion; // En días
    
    @NotNull(message = "El valor del producto es obligatorio")
    @Column(nullable = false)
    private BigDecimal valor;

    public ProductoControlModel() {
    }

    public ProductoControlModel(String registroICA, String nombreProducto, Integer frecuenciaAplicacion, BigDecimal valor) {
        this.registroICA = registroICA;
        this.nombreProducto = nombreProducto;
        this.frecuenciaAplicacion = frecuenciaAplicacion;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistroICA() {
        return registroICA;
    }

    public void setRegistroICA(String registroICA) {
        this.registroICA = registroICA;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getFrecuenciaAplicacion() {
        return frecuenciaAplicacion;
    }

    public void setFrecuenciaAplicacion(Integer frecuenciaAplicacion) {
        this.frecuenciaAplicacion = frecuenciaAplicacion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
