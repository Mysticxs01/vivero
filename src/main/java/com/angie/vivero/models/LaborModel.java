package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "labores")
public class LaborModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "La fecha de la labor es obligatoria")
    @Column(nullable = false)
    private LocalDate fecha;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false)
    private String descripcion;
    
    @NotNull(message = "El vivero es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vivero_id", nullable = false)
    private ViveroModel vivero;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_control_id")
    private ProductoControlModel productoControl;

    public LaborModel() {
    }

    public LaborModel(LocalDate fecha, String descripcion, ViveroModel vivero) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.vivero = vivero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ViveroModel getVivero() {
        return vivero;
    }

    public void setVivero(ViveroModel vivero) {
        this.vivero = vivero;
    }

    public ProductoControlModel getProductoControl() {
        return productoControl;
    }

    public void setProductoControl(ProductoControlModel productoControl) {
        this.productoControl = productoControl;
    }
}
