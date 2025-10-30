package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viveros")
public class ViveroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Column(nullable = false)
    private String codigo;
    
    @NotBlank(message = "El tipo de cultivo es obligatorio")
    @Column(nullable = false)
    private String tipoCultivo;
    
    @NotNull(message = "La finca es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finca_id", nullable = false)
    private FincaModel finca;
    
    @OneToMany(mappedBy = "vivero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LaborModel> labores = new ArrayList<>();

    public ViveroModel() {
    }

    public ViveroModel(String codigo, String tipoCultivo, FincaModel finca) {
        this.codigo = codigo;
        this.tipoCultivo = tipoCultivo;
        this.finca = finca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
    }

    public FincaModel getFinca() {
        return finca;
    }

    public void setFinca(FincaModel finca) {
        this.finca = finca;
    }

    public List<LaborModel> getLabores() {
        return labores;
    }

    public void setLabores(List<LaborModel> labores) {
        this.labores = labores;
    }
    
    public void addLabor(LaborModel labor) {
        labores.add(labor);
        labor.setVivero(this);
    }
    
    public void removeLabor(LaborModel labor) {
        labores.remove(labor);
        labor.setVivero(null);
    }
}

