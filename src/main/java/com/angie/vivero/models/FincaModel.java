package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fincas")
public class FincaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El número de catastro es obligatorio")
    @Column(nullable = false, unique = true)
    private String numeroCatastro;
    
    @NotBlank(message = "El municipio es obligatorio")
    @Column(nullable = false)
    private String municipio;
    
    @NotNull(message = "El productor es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productor_id", nullable = false)
    private ProductorModel productor;
    
    @OneToMany(mappedBy = "finca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ViveroModel> viveros = new ArrayList<>();

    public FincaModel() {
    }

    public FincaModel(String numeroCatastro, String municipio, ProductorModel productor) {
        this.numeroCatastro = numeroCatastro;
        this.municipio = municipio;
        this.productor = productor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCatastro() {
        return numeroCatastro;
    }

    public void setNumeroCatastro(String numeroCatastro) {
        this.numeroCatastro = numeroCatastro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public ProductorModel getProductor() {
        return productor;
    }

    public void setProductor(ProductorModel productor) {
        this.productor = productor;
    }

    public List<ViveroModel> getViveros() {
        return viveros;
    }

    public void setViveros(List<ViveroModel> viveros) {
        this.viveros = viveros;
    }
    
    public void addVivero(ViveroModel vivero) {
        viveros.add(vivero);
        vivero.setFinca(this);
    }
    
    public void removeVivero(ViveroModel vivero) {
        viveros.remove(vivero);
        vivero.setFinca(null);
    }
}
